package Presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Presentation.Configuracion.PlayerConfig;

public class SkinSelector extends JDialog {
    public SkinSelector(JFrame parent) {
        super(parent, "Seleccionar Skin", true); // true = modal
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(1, 3));
        ButtonGroup group = new ButtonGroup();

        String[] imagePaths = {
                "/Tiles/tile_0112.png",
                "/Tiles/tile_0109.png",
                "/Tiles/tile_0100.png"
        };

        for (String path : imagePaths) {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JToggleButton button = new JToggleButton(new ImageIcon(scaledImage));
            button.setPreferredSize(new Dimension(100, 100));
            button.setActionCommand(path); // Se guarda la ruta como identificador

            group.add(button);
            gridPanel.add(button);
        }

        JButton selectButton = new JButton("Elegir");
        selectButton.addActionListener(e -> {
            ButtonModel selected = group.getSelection();
            if (selected != null) {
                PlayerConfig.selectedSkin = selected.getActionCommand(); // Asigna la skin
                dispose(); // Cierra la ventana del selector
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná una skin primero");
            }
        });

        add(gridPanel, BorderLayout.CENTER);
        add(selectButton, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }
}

