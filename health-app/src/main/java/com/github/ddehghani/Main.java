package com.github.ddehghani;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.ddehghani.controller.MainController;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> new MainController());
    }
}