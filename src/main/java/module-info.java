/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/4
 * description：
 */
module ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mfx.resources;
    requires fr.brouillard.oss.cssfx;
    requires MaterialFX;
    requires ibatis.core;
    requires org.apache.commons.collections4;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    opens ui to javafx.fxml;
    opens ui.controllers to javafx.fxml;
    exports ui;
    opens entity;
    opens dao;
    opens mapper;
    opens server;
    opens utils;
}