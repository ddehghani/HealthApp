package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.RadialGradientPaint;


public class GradientButton extends JButton {

    public GradientButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        Point center = new Point(width / 2, height / 2);
        float radius = Math.max(width, height);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(120, 90, 195), new Color(230, 180, 235)}; // purple to light purple
        RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(paint);
        g2d.fillRoundRect(0, 0, width, height, 10, 10);
        g2d.dispose();
        super.paintComponent(g);
    }
}
