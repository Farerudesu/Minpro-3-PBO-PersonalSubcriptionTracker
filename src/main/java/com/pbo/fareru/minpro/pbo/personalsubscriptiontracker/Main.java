package com.pbo.fareru.minpro.pbo.personalsubscriptiontracker;

import com.pbo.fareru.minpro.pbo.controller.LanggananController;
import com.pbo.fareru.minpro.pbo.view.LanggananView;

/**
 * 
 * @author Muhammad Fahriel (2509116050)
 */
public class Main {
    public static void main(String[] args) {
        LanggananView view = new LanggananView();
        LanggananController controller = new LanggananController(view);
        controller.jalankanAplikasi();
    }
}
