package Trapy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class Circulacion extends JFrame implements ActionListener {

    JMenuBar bm; // barra de menu
    JMenu mArchivo, mBD; // menus
    JMenuItem mBD_C, mBD_R, mBD_Actualizar_Menu, mBD_Eliminar_Menu; // submenus

    JLabel lbl, lbId, lbNumero, lbAsientos, lbModelo, lbMotor, lbFecha;
    JTextField txtId, txtNumero, txtAsientos, txtModelo, txtMotor, txtFecha;
    JButton crear, leer, actualizar, eliminar;

    JTable tablaCirculacion;
    JScrollPane scrollTabla;
    DefaultTableModel modeloTabla;

    Circulacion() {
        this.setTitle("CRUD de circulacion");
        this.setBounds(150, 50, 650, 620);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        bm = new JMenuBar();
        this.setJMenuBar(bm);

        mArchivo = new JMenu("Archivo");
        bm.add(mArchivo);

        mBD = new JMenu("Base de datos");
        bm.add(mBD);

        this.mBD_C = new JMenuItem("Crear");
        this.mBD_C.addActionListener(e -> agregar());
        mBD.add(mBD_C);

        this.mBD_R = new JMenuItem("Leer");
        this.mBD_R.addActionListener(e -> consultar());
        mBD.add(mBD_R);

        this.mBD_Actualizar_Menu = new JMenuItem(" Actualizar");
        this.mBD_Actualizar_Menu.addActionListener(e -> actualizarRegistro());
        mBD.add(mBD_Actualizar_Menu);

        this.mBD_Eliminar_Menu = new JMenuItem("Eliminar");
        this.mBD_Eliminar_Menu.addActionListener(e -> eliminarRegistro());
        mBD.add(mBD_Eliminar_Menu);

        this.lbl = new JLabel(" Circulacion xd ");
        this.lbl.setBounds(240, 15, 200, 30);
        this.add(lbl);

        int y = 60;

        this.lbId = new JLabel("ID");
        this.lbId.setBounds(50, y, 90, 30);
        this.add(lbId);
        this.txtId = new JTextField("1");
        this.txtId.setBounds(150, y, 80, 30);
        this.add(txtId);

        y += 40;
        this.lbNumero = new JLabel("Numero");
        this.lbNumero.setBounds(50, y, 90, 30);
        this.add(lbNumero);
        this.txtNumero = new JTextField("67A-M7U");
        this.txtNumero.setBounds(150, y, 220, 30);
        this.add(txtNumero);

        y += 40;
        this.lbAsientos = new JLabel("Asientos");
        this.lbAsientos.setBounds(50, y, 90, 30);
        this.add(lbAsientos);
        this.txtAsientos = new JTextField("5");
        this.txtAsientos.setBounds(150, y, 220, 30);
        this.add(txtAsientos);

        y += 40;
        this.lbModelo = new JLabel("Modelo");
        this.lbModelo.setBounds(50, y, 90, 30);
        this.add(lbModelo);
        this.txtModelo = new JTextField("Bochito");
        this.txtModelo.setBounds(150, y, 220, 30);
        this.add(txtModelo);

        y += 40;
        this.lbMotor = new JLabel("Motor");
        this.lbMotor.setBounds(50, y, 90, 30);
        this.add(lbMotor);
        this.txtMotor = new JTextField("Eléctrico");
        this.txtMotor.setBounds(150, y, 220, 30);
        this.add(txtMotor);

        y += 40;
        this.lbFecha = new JLabel("Fecha");
        this.lbFecha.setBounds(50, y, 90, 30);
        this.add(lbFecha);
        this.txtFecha = new JTextField("2026-10-18");
        this.txtFecha.setBounds(150, y, 220, 30);
        this.add(txtFecha);

        int yBotones = 300;
        this.crear = new JButton("Crear");
        this.crear.setBounds(50, yBotones, 110, 30);
        this.add(crear);

        this.leer = new JButton("Leer");
        this.leer.setBounds(170, yBotones, 110, 30);
        this.add(this.leer);

        this.actualizar = new JButton("Actualizar");
        this.actualizar.setBounds(290, yBotones, 110, 30);
        this.add(this.actualizar);

        this.eliminar = new JButton("Eliminar");
        this.eliminar.setBounds(410, yBotones, 110, 30);
        this.add(this.eliminar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Numero");
        modeloTabla.addColumn("Asientos");
        modeloTabla.addColumn("Modelo");
        modeloTabla.addColumn("Motor");
        modeloTabla.addColumn("Fecha");

        tablaCirculacion = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaCirculacion);
        scrollTabla.setBounds(50, 350, 520, 200);
        this.add(scrollTabla);

        tablaCirculacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaCirculacion.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(String.valueOf(modeloTabla.getValueAt(fila, 0)));
                    txtNumero.setText(String.valueOf(modeloTabla.getValueAt(fila, 1)));
                    txtAsientos.setText(String.valueOf(modeloTabla.getValueAt(fila, 2)));
                    txtModelo.setText(String.valueOf(modeloTabla.getValueAt(fila, 3)));
                    txtMotor.setText(String.valueOf(modeloTabla.getValueAt(fila, 4)));
                    Object fechaObj = modeloTabla.getValueAt(fila, 5);
                    txtFecha.setText(fechaObj == null ? "" : fechaObj.toString());
                }
            }
        });

        this.crear.addActionListener(this);
        this.leer.addActionListener(this);
        this.actualizar.addActionListener(this);
        this.eliminar.addActionListener(this);

        consultar();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Circulacion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.leer) {
            consultar();
        }
        if (e.getSource() == this.crear) {
            agregar();
        }
        if (e.getSource() == this.actualizar) {
            actualizarRegistro();
        }
        if (e.getSource() == this.eliminar) {
            eliminarRegistro();
        }
    }

    public void agregar() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/trapy", "root", "");
            PreparedStatement ps = cnx.prepareStatement("insert into circulacion(Numero, Asientos, Modelo, Motor, Fecha) values(?, ?, ?, ?, ?)");
            ps.setString(1, this.txtNumero.getText());
            ps.setString(2, this.txtAsientos.getText());
            ps.setString(3, this.txtModelo.getText());
            ps.setString(4, this.txtMotor.getText());
            ps.setDate(5, Date.valueOf(this.txtFecha.getText()));
            ps.execute();
            JOptionPane.showMessageDialog(this, "Circulacion agregada con éxito");
            cnx.close();
            consultar();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa yyyy-MM-dd");
        }
    }

    public void consultar() {
        try {
            modeloTabla.setRowCount(0);
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/trapy", "root", "");
            PreparedStatement ps = cnx.prepareStatement("select * from circulacion");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id")
                        + " - Numero: " + rs.getString("numero")
                        + " - Asientos: " + rs.getString("asientos")
                        + " - Modelo: " + rs.getString("modelo")
                        + " - Motor: " + rs.getString("motor")
                        + " - Fecha: " + rs.getDate("fecha"));

                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getString("asientos"),
                    rs.getString("modelo"),
                    rs.getString("motor"),
                    rs.getDate("fecha")
                };
                modeloTabla.addRow(fila);
            }
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void actualizarRegistro() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/trapy", "root", "");
            PreparedStatement ps = cnx.prepareStatement("update circulacion set Numero = ?, Asientos = ?, Modelo = ?, Motor = ?, Fecha = ? where id = ?");
            ps.setString(1, this.txtNumero.getText());
            ps.setString(2, this.txtAsientos.getText());
            ps.setString(3, this.txtModelo.getText());
            ps.setString(4, this.txtMotor.getText());
            ps.setDate(5, Date.valueOf(this.txtFecha.getText()));
            ps.setInt(6, Integer.parseInt(this.txtId.getText()));
            ps.execute();
            JOptionPane.showMessageDialog(this, "Registro actualizado");
            cnx.close();
            consultar();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa yyyy-MM-dd");
        }
    }

    public void eliminarRegistro() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/trapy", "root", "");
            PreparedStatement ps = cnx.prepareStatement("delete from circulacion where id = ?");
            ps.setInt(1, Integer.parseInt(this.txtId.getText()));
            ps.execute();
            JOptionPane.showMessageDialog(this, "Registro eliminado");
            cnx.close();
            consultar();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar: " + ex.getMessage());
        }
    }
}
