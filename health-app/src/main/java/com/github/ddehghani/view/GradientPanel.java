package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.RadialGradientPaint;

public class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        Point center = new Point(width / 2, height / 2);
        float radius = Math.max(width, height);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(120, 90, 195), new Color(255, 255, 255)}; // purple to white
        RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
    }
}