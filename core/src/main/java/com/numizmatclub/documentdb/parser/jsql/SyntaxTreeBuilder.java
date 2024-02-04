package com.numizmatclub.documentdb.parser.jsql;

import com.numizmatclub.documentdb.parser.SqlParserException;
import com.numizmatclub.documentdb.parser.expression.*;
import com.numizmatclub.documentdb.parser.function.SQLFunction;
import com.numizmatclub.documentdb.parser.function.SQLFunctions;
import com.numizmatclub.documentdb.storage.StorageEngineException;
import com.numizmatclub.documentdb.utils.ValueHolder;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Valerii Kantor
 */
public class SyntaxTreeBuilder {

    public BooleanExpression buildTree(Expression expression) {
        return buildNode(expression);
    }

    private BooleanExpression buildNode(Expression expression) {
        if (expression instanceof Parenthesis parenthesis) {
            expression = parenthesis.getExpression();
        }

        ValueHolder<BooleanExpression> nodeExpression = new ValueHolder<>();
        expression.accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(AndExpression expr) {
                BooleanExpression left = buildNode(expr.getLeftExpression());
                BooleanExpression right = buildNode(expr.getRightExpression());
                AndConditionExpression andConditionExpression = new AndConditionExpression(left, right);
                nodeExpression.setValue(andConditionExpression);
            }

            @Override
            public void visit(OrExpression expr) {
                BooleanExpression left = buildNode(expr.getLeftExpression());
                BooleanExpression right = buildNode(expr.getRightExpression());
                OrConditionExpression orConditionExpression = new OrConditionExpression(left, right);
                nodeExpression.setValue(orConditionExpression);
            }

            @Override
            public void visit(Between expr) {
                String fieldName = expr.getLeftExpression(Column.class).getName(false);

                Expression betweenExpressionStart = expr.getBetweenExpressionStart();
                ValueExpression betweenStart = buildValueNode(betweenExpressionStart);
                Expression betweenExpressionEnd = expr.getBetweenExpressionEnd();
                ValueExpression betweenEnd = buildValueNode(betweenExpressionEnd);

                BetweenExpression betweenExpression = new BetweenExpression(fieldName, betweenStart, betweenEnd);
                nodeExpression.setValue(betweenExpression);
            }

            @Override
            public void visit(InExpression expr) {
                String fieldName = expr.getLeftExpression(Column.class).getColumnName();
                List<Expression> expressions = ((ExpressionList) expr.getRightItemsList())
                        .getExpressions();

                //never empty; JSQL does not parse a query with an empty IN values
                Class<?> expressionType = expressions.get(0).getClass();
                for (Expression valueListExpression : expressions) {
                    if (!valueListExpression.getClass().equals(expressionType)) {
                        throw new StorageEngineException("All values in 'IN' expression should have the same type");
                    }
                }

                List<ValueExpression> valueExpressions = expressions
                        .stream()
                        .map(SyntaxTreeBuilder.this::buildValueNode)
                        .toList();

                InConditionExpression inConditionExpression = new InConditionExpression(fieldName, valueExpressions);
                nodeExpression.setValue(inConditionExpression);
            }

            @Override
            public void visit(Column column) {

            }

            @Override
            public void visit(EqualsTo expr) {
                ValueExpression left = buildValueNode(expr.getLeftExpression());
                ValueExpression right = buildValueNode(expr.getRightExpression());
                BinaryConditionExpression binaryConditionExpression =
                        new BinaryConditionExpression(left, right, new EqualsCompareOperator());

                nodeExpression.setValue(binaryConditionExpression);
            }

            @Override
            public void visit(GreaterThan expr) {
                ValueExpression left = buildValueNode(expr.getLeftExpression());
                ValueExpression right = buildValueNode(expr.getRightExpression());

                BinaryConditionExpression binaryConditionExpression =
                        new BinaryConditionExpression(left, right, new GtCompareOperator());

                nodeExpression.setValue(binaryConditionExpression);
            }

            @Override
            public void visit(GreaterThanEquals expr) {
                ValueExpression left = buildValueNode(expr.getLeftExpression());
                ValueExpression right = buildValueNode(expr.getRightExpression());

                BinaryConditionExpression binaryConditionExpression =
                        new BinaryConditionExpression(left, right, new GeCompareOperator());

                nodeExpression.setValue(binaryConditionExpression);
            }

            // Less expression
            @Override
            public void visit(MinorThan expr) {
                ValueExpression left = buildValueNode(expr.getLeftExpression());
                ValueExpression right = buildValueNode(expr.getRightExpression());

                BinaryConditionExpression binaryConditionExpression =
                        new BinaryConditionExpression(left, right, new LtCompareOperator());

                nodeExpression.setValue(binaryConditionExpression);
            }

            @Override
            public void visit(MinorThanEquals expr) {
                ValueExpression left = buildValueNode(expr.getLeftExpression());
                ValueExpression right = buildValueNode(expr.getRightExpression());

                BinaryConditionExpression binaryConditionExpression =
                        new BinaryConditionExpression(left, right, new LeCompareOperator());

                nodeExpression.setValue(binaryConditionExpression);
            }

            @Override
            public void visit(NotEqualsTo expr) {
                ValueExpression left = buildValueNode(expr.getLeftExpression());
                ValueExpression right = buildValueNode(expr.getRightExpression());

                BinaryConditionExpression binaryConditionExpression =
                        new BinaryConditionExpression(left, right, new NotEqualsCompareOperator());

                nodeExpression.setValue(binaryConditionExpression);
            }
        });

        return nodeExpression.getValue();
    }

    private ValueExpression buildValueNode(Expression expression) {
        if (expression instanceof Parenthesis parenthesis) {
            expression = parenthesis.getExpression();
        }

        ValueHolder<ValueExpression> nodeValueExpression = new ValueHolder<>();

        expression.accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(Column column) {
                String name = column.getName(false);
                FiledValueExpression valueExpression = new FiledValueExpression(name);
                nodeValueExpression.setValue(valueExpression);
            }

            @Override
            public void visit(LongValue value) {
                ConstantValueExpression valueExpression = new ConstantValueExpression(value.getValue(), Long.class);
                nodeValueExpression.setValue(valueExpression);
            }

            @Override
            public void visit(DoubleValue value) {
                ConstantValueExpression valueExpression = new ConstantValueExpression(value.getValue(), Double.class);
                nodeValueExpression.setValue(valueExpression);
            }

            @Override
            public void visit(StringValue value) {
                ConstantValueExpression valueExpression = new ConstantValueExpression(value.getValue(), String.class);
                nodeValueExpression.setValue(valueExpression);
            }

            @Override
            public void visit(TimeValue value) {
                super.visit(value);
            }

            @Override
            public void visit(Function function) {
                boolean isAllFields = false;
                List<ValueExpression> mappedParameters = new ArrayList<>();

                if (function.getParameters() != null) {
                    List<Expression> parameters = function.getParameters().getExpressions();
                    for (Expression parameterExpression : parameters) {
                        if (parameterExpression instanceof AllColumns) {
                            isAllFields = true;
                        } else {
                            mappedParameters.add(buildValueNode(parameterExpression));
                        }
                    }
                }

                String functionName = function.getName().toLowerCase();
                SQLFunction sqlFunction = SQLFunctions.getFunction(functionName);
                FunctionExpression functionExpression = new FunctionExpression(sqlFunction, mappedParameters, isAllFields);

                nodeValueExpression.setValue(functionExpression);
            }
        });
        return nodeValueExpression.getValue();
    }

    public List<ValueExpression> buildProjectionList(PlainSelect plainSelect) {
        List<ValueExpression> projection = new ArrayList<>();
        List<SelectItem> selectItems = plainSelect.getSelectItems();

        if (!(selectItems.get(0) instanceof AllColumns)) {
            selectItems.forEach(selectItem -> {
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                Expression selectExpression = selectExpressionItem.getExpression();
                ValueExpression valueExpression = buildValueNode(selectExpression);
                if (valueExpression == null) {
                    throw new SqlParserException(String.format("Select item '%s' is not supported", selectExpression.toString()));
                }
                projection.add(valueExpression);
            });
        }

        return projection;
    }
}
