package com.numizmatclub.documentdb.parser.jsql;

import com.numizmatclub.documentdb.parser.SqlParserException;
import com.numizmatclub.documentdb.parser.SqlStatement;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterSession;
import net.sf.jsqlparser.statement.alter.AlterSystemStatement;
import net.sf.jsqlparser.statement.alter.RenameTableStatement;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.analyze.Analyze;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.grant.Grant;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.show.ShowIndexStatement;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;

public class SqlStatementVisitorAdapter implements StatementVisitor {

    private SqlStatement statement;

    public SqlStatement getStatement() {
        return statement;
    }

    @Override
    public void visit(Analyze analyze) {
        throw new SqlParserException("Analyze query is not supported");
    }

    @Override
    public void visit(SavepointStatement savepointStatement) {
        throw new SqlParserException("Savepoint query is not supported");
    }

    @Override
    public void visit(RollbackStatement rollbackStatement) {
        throw new SqlParserException("Rollback query is not supported");
    }

    @Override
    public void visit(Comment comment) {
        throw new SqlParserException("Comment query is not supported");
    }

    @Override
    public void visit(Commit commit) {
        throw new SqlParserException("Commit query is not supported");
    }

    @Override
    public void visit(Delete delete) {
        throw new SqlParserException("Delete query is not supported");
    }

    @Override
    public void visit(Update update) {
        throw new SqlParserException("Update query is not supported");
    }

    @Override
    public void visit(Insert insert) {
        throw new SqlParserException("Insert query is not supported");
    }

    @Override
    public void visit(Replace replace) {
        throw new SqlParserException("Replace query is not supported");
    }

    @Override
    public void visit(Drop drop) {
        throw new SqlParserException("Drop query is not supported");
    }

    @Override
    public void visit(Truncate truncate) {
        throw new SqlParserException("Truncate query is not supported");
    }

    @Override
    public void visit(CreateIndex createIndex) {
        statement = JSqlCreateIndexQueryParser.parse(createIndex);
    }

    @Override
    public void visit(CreateSchema aThis) {
        throw new SqlParserException("CreateSchema query is not supported");
    }

    @Override
    public void visit(CreateTable createTable) {
        statement = JSqlCreateTableQueryParser.parse(createTable);
    }

    @Override
    public void visit(CreateView createView) {
        throw new SqlParserException("CreateView query is not supported");
    }

    @Override
    public void visit(AlterView alterView) {
        throw new SqlParserException("AlterView query is not supported");
    }

    @Override
    public void visit(Alter alter) {
        throw new SqlParserException("Alter query is not supported");
    }

    @Override
    public void visit(Statements stmts) {
        throw new SqlParserException("Statements query is not supported");
    }

    @Override
    public void visit(Execute execute) {
        throw new SqlParserException("Execute query is not supported");
    }

    @Override
    public void visit(SetStatement set) {
        throw new SqlParserException("Set query is not supported");
    }

    @Override
    public void visit(ResetStatement reset) {
        throw new SqlParserException("Reset query is not supported");
    }

    @Override
    public void visit(ShowColumnsStatement set) {
        throw new SqlParserException("ShowColumns query is not supported");
    }

    @Override
    public void visit(ShowIndexStatement showIndex) {
        throw new SqlParserException("ShowIndex query is not supported");
    }

    @Override
    public void visit(ShowTablesStatement showTables) {
        throw new SqlParserException("ShowTables query is not supported");
    }

    @Override
    public void visit(Merge merge) {
        throw new SqlParserException("Merge query is not supported");
    }

    @Override
    public void visit(Select select) {
        statement = JSqlSelectQueryParser.parse(select);
    }

    @Override
    public void visit(Upsert upsert) {
        throw new SqlParserException("Upsert query is not supported");
    }

    @Override
    public void visit(UseStatement use) {
        throw new SqlParserException("Use query is not supported");
    }

    @Override
    public void visit(Block block) {
        throw new SqlParserException("Block query is not supported");
    }

    @Override
    public void visit(ValuesStatement values) {
        throw new SqlParserException("Values query is not supported");
    }

    @Override
    public void visit(DescribeStatement describe) {
        throw new SqlParserException("Describe query is not supported");
    }

    @Override
    public void visit(ExplainStatement aThis) {
        throw new SqlParserException("Explain query is not supported");
    }

    @Override
    public void visit(ShowStatement aThis) {
        throw new SqlParserException("Show query is not supported");
    }

    @Override
    public void visit(DeclareStatement aThis) {
        throw new SqlParserException("Declare query is not supported");
    }

    @Override
    public void visit(Grant grant) {
        throw new SqlParserException("Grant query is not supported");
    }

    @Override
    public void visit(CreateSequence createSequence) {
        throw new SqlParserException("CreateSequence query is not supported");
    }

    @Override
    public void visit(AlterSequence alterSequence) {
        throw new SqlParserException("AlterSequence query is not supported");
    }

    @Override
    public void visit(CreateFunctionalStatement createFunctionalStatement) {
        throw new SqlParserException("CreateFunctional query is not supported");
    }

    @Override
    public void visit(CreateSynonym createSynonym) {
        throw new SqlParserException("CreateSynonym query is not supported");
    }

    @Override
    public void visit(AlterSession alterSession) {
        throw new SqlParserException("AlterSession query is not supported");
    }

    @Override
    public void visit(IfElseStatement aThis) {
        throw new SqlParserException("IfElse query is not supported");
    }

    @Override
    public void visit(RenameTableStatement renameTableStatement) {
        throw new SqlParserException("RenameTable query is not supported");
    }

    @Override
    public void visit(PurgeStatement purgeStatement) {
        throw new SqlParserException("Purge query is not supported");
    }

    @Override
    public void visit(AlterSystemStatement alterSystemStatement) {
        throw new SqlParserException("AlterSystem query is not supported");
    }

    @Override
    public void visit(UnsupportedStatement unsupportedStatement) {
        throw new SqlParserException("Unsupported query is not supported");
    }
}
