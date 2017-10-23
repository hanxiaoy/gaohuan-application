package com.gaohuan.sql.common;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by gaohuan on 2017/10/23.
 */
public class TablesFinder extends TablesFinderAdapter {

    private Set<Table> tables;

    private TablesFinder() {
        tables = new CopyOnWriteArraySet<>();
    }

    public static TablesFinder create() {
        return new TablesFinder();
    }

    @Override
    public void visit(Update update) {
        for (Table table : update.getTables()) {
            tables.add(table);
        }
        if (update.getFromItem() != null) {
            update.getFromItem().accept(this);
        }

        if (update.getJoins() != null) {
            for (Join join : update.getJoins()) {
                join.getRightItem().accept(this);
            }
        }
    }

    @Override
    public void visit(Insert insert) {
        tables.add(insert.getTable());

    }

    @Override
    public void visit(Select select) {
        select.getSelectBody().accept(this);

    }


    @Override
    public void visit(Table tableName) {
        tables.add(tableName);
    }


    @Override
    public void visit(PlainSelect plainSelect) {
        if (plainSelect.getFromItem() != null) {
            plainSelect.getFromItem().accept(this);
        }

        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                join.getRightItem().accept(this);
            }
        }
    }

    public Set<Table> getTables(Statement statement) {
        statement.accept(this);
        return tables;
    }
}
