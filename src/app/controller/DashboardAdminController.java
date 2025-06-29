package app.controller;

import main.Index;
import resource.view.admin.DashboardAdmin;

public class DashboardAdminController {
    private DashboardAdmin viewDashboardAdmin;
    private Index app;

    public DashboardAdminController(Index app){
        this.app = app;
        this.viewDashboardAdmin = new DashboardAdmin(app);
    }

    public void tampilkanData(){
        viewDashboardAdmin.setBorrowTotalLabel(100);
        viewDashboardAdmin.setMemberTotalLabel(200);
    }

    public DashboardAdmin getView(){
        return viewDashboardAdmin;
    }
}
