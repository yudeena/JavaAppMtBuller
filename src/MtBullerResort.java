import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import java.awt.event.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Properties;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;




/**
 * Represents the Mt Buller Resort management system.
 * Allows users to manage accommodations, customers, and deals.
 */
public class MtBullerResort extends JFrame implements ActionListener {

    private final ArrayList<Accomodation> accomodations;
    private final ArrayList<Customer> customers;
    private ArrayList<Deal> deals;



    private JTabbedPane tabbedPane;

    /**
     * Initializes MtBullerResort with empty lists for accommodations, customers, and deals.
     */
    public MtBullerResort() {
        accomodations = new ArrayList<>();
        customers = new ArrayList<>();
        deals = new ArrayList<>();
        populateLists();
        createAndShowGUI();
    }


    /**
     * Populates lists of accommodations and customers with initial data.
     */
    public void populateLists() {
        Accomodation[] arrOfAccomodations = {
                new Accomodation("Single", 130),
                new Accomodation("Double", 240),
                new Accomodation("Twin", 200),
                new Accomodation("Suite", 350),
                new Accomodation("Chalet", 500),
                new Accomodation("Studio", 180),
                new Accomodation("Cabin", 160),
                new Accomodation("Penthouse", 700),
                new Accomodation("Villa", 600),
                new Accomodation("Bungalow", 450)
        };
        Customer[] arrOfCustomers = {
                new Customer("Amirah", "Beginner"),
                new Customer("Juanita", "Intermediate"),
                new Customer("Tariq", "Expert")
        }; // Initial data for accommodations and customers
        Collections.addAll(accomodations, arrOfAccomodations);
        Collections.addAll(customers, arrOfCustomers);
    }

    /**
     * Runs the Mt Buller Resort management system.
     */
    public void createAndShowGUI() {
        // Basic frame settings
        setTitle("❆ MT BULLER RESORT ❆");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        UIManager.put("Panel.background", new Color(249, 248, 250));
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);
        // Create the main panel with EmptyBorder
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        // Initialize tabbedPane
        tabbedPane = new JTabbedPane();

        // Adding menu tab
        tabbedPane.addTab("Menu", createMenuPanel());

        // Adding tabbedPane to the main panel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Set the main panel as the content pane of the frame
        setContentPane(mainPanel);

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Creates the menu panel with various navigation and action buttons.
     */
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Create navPanel
        JPanel navPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Adjust insets for padding
        gbc.weighty = 0.0; // Ensures that the navPanel does not take additional vertical space
        gbc.anchor = GridBagConstraints.NORTH;

// Icon on the left
        JLabel iconLabel = null;
        try {
            URL url = new URL("https://www.mtbuller.com.au/hubfs/logos/100y%20lockup%20rgb%20horiz3.png");
            BufferedImage originalImage = ImageIO.read(url);
            Image resizedImage = originalImage.getScaledInstance(150, 50, Image.SCALE_SMOOTH);
            iconLabel = new JLabel(new ImageIcon(resizedImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert iconLabel != null;
        navPanel.add(iconLabel, gbc);

// Load and resize the images for the save and read buttons
        ImageIcon saveIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/save.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon readIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/read.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

// Create save button with the save icon
        JButton saveButton = new JButton(saveIcon);
        saveButton.setPreferredSize(new Dimension(20, 20));
        saveButton.setMaximumSize(new Dimension(20, 20));
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false); // Make the button transparent
        saveButton.setFocusPainted(false);
        saveButton.setActionCommand("SAVE"); // Set action command
        saveButton.addActionListener(this); // Add action listener

// Create read button with the read icon
        JButton readButton = new JButton(readIcon);
        readButton.setPreferredSize(new Dimension(25, 25));
        readButton.setMaximumSize(new Dimension(25, 25));
        readButton.setBorderPainted(false);
        readButton.setContentAreaFilled(false); // Make the button transparent
        readButton.setFocusPainted(false);
        readButton.setActionCommand("READ"); // Set action command
        readButton.addActionListener(this); // Add action listener

// Add buttons to the buttonPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between buttons
        buttonPanel.add(readButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between buttons

// NEW PROFILE button
        JButton newProfileButton = new JButton("NEW PROFILE");
        newProfileButton.setPreferredSize(new Dimension(120, 30));
        newProfileButton.setMaximumSize(new Dimension(120, 30));
        newProfileButton.setForeground(new Color(0, 20, 137)); // Blue text color
        newProfileButton.setBackground(Color.WHITE); // White background color
        newProfileButton.setBorder(BorderFactory.createLineBorder(new Color(0, 20, 137))); // Blue border
        newProfileButton.setFocusPainted(false);
        newProfileButton.setActionCommand("NEW PROFILE"); // Set action command
        newProfileButton.addActionListener(this); // Add action listener

        buttonPanel.add(newProfileButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between buttons

// BOOK NOW button
        JButton bookNowButton = new JButton("BOOK NOW");
        bookNowButton.setPreferredSize(new Dimension(120, 30));
        bookNowButton.setMaximumSize(new Dimension(120, 30));
        bookNowButton.setForeground(Color.WHITE); // White text color
        bookNowButton.setBackground(new Color(0, 20, 137)); // Blue background color
        bookNowButton.setBorder(BorderFactory.createLineBorder(new Color(0, 20, 137))); // Blue border
        bookNowButton.setFocusPainted(false);
        bookNowButton.setActionCommand("BOOK NOW"); // Set action command
        bookNowButton.addActionListener(this); // Add action listener
        buttonPanel.add(bookNowButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        navPanel.add(buttonPanel, gbc);


// Define the size of the image panel
        int panelWidth = 800;
        int panelHeight = 200; // Adjust height as needed

// Load and resize image
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/homepage.jpg"));
        Image originalImage = originalIcon.getImage();

// Scale the image proportionally to fit within the panel dimensions
        Image resizedImage = originalImage.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

// Create a panel for the image
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        imagePanel.setMaximumSize(new Dimension(panelWidth, panelHeight));
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

// Adding image
        JLabel imageLabel = new JLabel(resizedIcon);
        imagePanel.add(imageLabel);

// Create headerPanel and set its layout
        JPanel headerPanel = new JPanel(new BorderLayout());

// Add navPanel to the north of headerPanel
        headerPanel.add(navPanel, BorderLayout.NORTH);

// Add imagePanel to the south of headerPanel
        headerPanel.add(imagePanel, BorderLayout.SOUTH);

// Add headerPanel to menuPanel
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(headerPanel, BorderLayout.NORTH);

        // Create the optionPanel
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(1, 3, 20, 10));
        optionPanel.setBackground(Color.WHITE);

        // Add ActionListeners to the optionPanel buttons
        optionPanel.add(createButton("Lift Passes", "https://www.mtbuller.com.au/hubfs/lifts.png", this));
        optionPanel.add(createButton("Lessons", "https://www.mtbuller.com.au/hubfs/lesson.png", this));
        optionPanel.add(createButton("Accommodation", "https://www.mtbuller.com.au/hubfs/stay.png", this));
        optionPanel.add(createButton("Search Bookings", "https://www.mtbuller.com.au/hubfs/entry.png", this));

        // Create a wrapper panel for the optionPanel with padding
        JPanel optionWrapperPanel = new JPanel(new BorderLayout());
        optionWrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // Add desired padding
        optionWrapperPanel.add(optionPanel, BorderLayout.CENTER);

        // Create the selectionPanel and add the optionWrapperPanel to it
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));

        selectionPanel.add(optionWrapperPanel);
        selectionPanel.add(Box.createVerticalStrut(10));

        // Create the centerContainer
        JPanel centerContainer = createCenterContainer();
        centerContainer.setPreferredSize(new Dimension(centerContainer.getPreferredSize().width, 150)); // Set a fixed height

        // Create the quit button panel
        JPanel quitButtonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning

        //Testing
        ImageIcon quitIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/exit.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon homeIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/home.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));


// Load the image, resize it, and create the quit button with the resized image
        JButton quitButton = new JButton(quitIcon);
        quitButton.setPreferredSize(new Dimension(20, 20));
        quitButton.setMaximumSize(new Dimension(20, 20));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false); // Make the button transparent
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        //Home Button
        JButton homeButton = new JButton(homeIcon);
        homeButton.setPreferredSize(new Dimension(25, 25));
        homeButton.setMaximumSize(new Dimension(25, 25));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false); // Make the button transparent
        homeButton.setFocusPainted(false);

        homeButton.addActionListener(e -> createMenuPanel());

// Add the quit button to the east side of the panel
        quitButtonPanel.add(homeButton, BorderLayout.WEST);
        quitButtonPanel.add(quitButton, BorderLayout.EAST);


        // Create the southPanel
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(centerContainer, BorderLayout.NORTH);
        southPanel.add(quitButtonPanel, BorderLayout.SOUTH);

        // Add the selectionPanel and southPanel to the menuPanel
        menuPanel.add(selectionPanel, BorderLayout.CENTER);
        menuPanel.add(southPanel, BorderLayout.SOUTH);


        return menuPanel;

    }
    /**
     * Creates the center container with two columns: content and customer list panels.
     */
    private JPanel createCenterContainer() {
        JPanel panel = new JPanel(new BorderLayout());

        // Wrapper panel for content with GridBagLayout
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 10); // Add desired insets

        // Main content panel for the first column (70% width) with image
        JPanel contentPanel1 = createContentPanelWithImage(
        );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.7; // 70% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;

        wrapperPanel.add(contentPanel1, gbc);

        // Different content panel for the second column (30% width)
        JPanel contentPanel2 = createCustomerListPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // 30% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;

        wrapperPanel.add(contentPanel2, gbc);

        // Add the wrapper panel to the main panel with border
        JPanel wrapperWithInsets = new JPanel(new BorderLayout());
        wrapperWithInsets.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add desired insets
        wrapperWithInsets.add(wrapperPanel, BorderLayout.CENTER);

        panel.add(wrapperWithInsets, BorderLayout.CENTER);

        return panel;
    }
    /**
     * Creates a content panel with an image and text for the center container.
     */
    private JPanel createContentPanelWithImage() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 0); // Add desired insets

        // Create text panel
        JPanel textPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Never been to Mt Buller?", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea("Head to Mt Buller feeling like a seasoned pro with our easy guide for first-timers, ensuring you're all set for an unforgettable snow adventure.");
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        textPanel.add(descriptionArea, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.7; // 70% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(textPanel, gbc);

        // Load the image
        java.net.URL imageUrl = getClass().getClassLoader().getResource("images/centercontainer.jpg");
        if (imageUrl == null) {
            System.out.println("Image file not found: " + "images/centercontainer.jpg");
            return panel;
        }
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        Image image = imageIcon.getImage(); // Get the original image

        // Optionally scale the image
        Image scaledImage = image.getScaledInstance(200, -1, Image.SCALE_SMOOTH); // Scale image while maintaining aspect ratio
        imageIcon = new ImageIcon(scaledImage); // Create a new ImageIcon with the scaled image

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(200, imageIcon.getIconHeight())); // Ensure the label has a size

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // 30% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(imageLabel, gbc);

        return panel;
    }

    /**
     * Creates the customer list panel with a table displaying customer information.
     */
    private JPanel createCustomerListPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("CUSTOMER LIST ", SwingConstants.RIGHT) {
            @Override
            protected void paintComponent(Graphics g) {
                // Enable antialiasing for smoother text
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Measure the text width
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(getText());

                // Set the background color to the width of the text
                g.setColor(new Color(8, 20, 140));
                g.fillRect(getWidth() - textWidth, 0, textWidth, getHeight());

                super.paintComponent(g);
            }
        };

        titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16)); // Set font to bold and italic
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        titleLabel.setOpaque(false); // Make the label non-opaque to customize painting

        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Customer table
        String[] columnNames = {"Customer ID", "Name", "Skiing Level"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Update the table model when customers are added
        updateCustomerTable(model);

        // Hyperlink label for "All Customers"
        JLabel allCustomersLabel = new JLabel("<html><a href=''>All Customers</a></html>");
        allCustomersLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        allCustomersLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listCustomers();
            }
        });

        contentPanel.add(allCustomersLabel, BorderLayout.SOUTH);

        return contentPanel;
    }
    /**
     * Updates the customer table with the latest customer information.
     */
    private void updateCustomerTable(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing rows
        for (Customer customer : customers) {
            Object[] row = {
                    customer.getCustId(),
                    customer.getName(),
                    customer.getSkiingLevel(),
            };
            model.addRow(row);
        }
    }
    /**
     * Creates a button with text, an icon, and an action listener.
     */
    private static JButton createButton(String text, String iconUrl, ActionListener listener) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBorder(new CompoundBorder(
                new MatteBorder(0, 1, 0, 1, new Color(211, 211, 211)), // Light grey borders on the left and right sides
                new EmptyBorder(10, 10, 10, 10))); // Padding inside the button


        try {
            URL url = new URL(iconUrl);
            ImageIcon icon = new ImageIcon(url);
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
            button.add(iconLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Font font = new Font("Arial", Font.BOLD, 10); // Define the font
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(new Color(8, 20, 140));
        textLabel.setFont(font); // Apply the font to the text label
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(textLabel, BorderLayout.SOUTH);

        button.setActionCommand(text); // Set action command to match the text
        button.addActionListener(listener); // Add action listener

        return button;
    }
    /**
     * Handles actions performed on various buttons in the application.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Accommodation":
                displayAllAccomodations();
                break;
            case "NEW PROFILE":
                addCustomer();
                break;
            case "Customers":
                listCustomers();
                break;
            case "BOOK NOW":
                addDeal();
                break;
            case "Search Bookings":
                listDeals();
                break;
            case "Lift Passes":
                addPassToDeal();
                break;
            case "Lessons":
                lessonPanel();
                break;
            case "SAVE":
                saveDeals();
                break;
            case "READ":
                readDeals();
                break;
            case "QUIT":
                System.exit(0);
                break;
            default:
                showMessage("No action defined for: " + actionCommand, "Message");
        }
    }
    /**
     * Displays a message in a new tab.
     */

    private void showMessage(String message, String tabTitle) {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        tabbedPane.addTab(tabTitle, panel);
        tabbedPane.setSelectedComponent(panel);
    }
    /**
     * Removes a tab by its component.
     */

    // Method to remove a tab by its component
    private void removeTab(Component component) {
        int index = tabbedPane.indexOfComponent(component);
        if (index != -1) {
            tabbedPane.remove(index);
        }
    }

    /**
     * New Case 1: combines former displayAccommodations() and displayAvailableAccommodations()
     * by using a "sort" function to filter All vs available accommodation.
     */
    private void displayAccommodations(List<Accomodation> accommodations) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel tablePanel = new JPanel(new GridLayout(0, 2, 20, 10)); // 2 columns, dynamic rows

        // Create a main container panel for the table and header
        JPanel mainContainerPanel = new JPanel(new BorderLayout(10, 0)); // Add a 5% gap between the panels

        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/otherheader.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        panel.add(imagePanel, BorderLayout.NORTH);


        // Header panel with search fields
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(new Color(8, 20, 140));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel searchLabelPanel = new JPanel();
        searchLabelPanel.setBackground(new Color(8, 20, 140)); // Set background color
        JLabel searchLabel = new JLabel("SEARCH ALL PROPERTIES");
        searchLabel.setForeground(Color.WHITE); // Set text color to white
        searchLabelPanel.add(searchLabel);
        headerPanel.add(searchLabelPanel, BorderLayout.NORTH);

        searchPanel.add(Box.createHorizontalStrut(20)); // Spacer

        JPanel arrivalPanel = new JPanel(new BorderLayout());
        arrivalPanel.setBackground(new Color(8, 20, 140)); // Set background color
        JLabel arrivalLabel = new JLabel("ARRIVAL");
        arrivalLabel.setForeground(Color.WHITE); // Set text color to white
        arrivalPanel.add(arrivalLabel, BorderLayout.NORTH);



        SqlDateModel arrivalModel = new SqlDateModel();
        LocalDate currentDate = LocalDate.now();
        arrivalModel.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        arrivalModel.setSelected(true);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl arrivalDatePanel = new JDatePanelImpl(arrivalModel, p);
        JDatePickerImpl arrivalDatePicker = new JDatePickerImpl(arrivalDatePanel, new DateLabelFormatter());

        // Disable past dates
        arrivalDatePanel.getModel().addChangeListener(e -> {
            LocalDate selectedDate = LocalDate.of(arrivalModel.getYear(), arrivalModel.getMonth() + 1, arrivalModel.getDay());
            if (selectedDate.isBefore(currentDate)) {
                arrivalModel.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
            }
        });

        arrivalPanel.add(arrivalDatePicker, BorderLayout.SOUTH);
        searchPanel.add(arrivalPanel);

        searchPanel.add(Box.createHorizontalStrut(20)); // Spacer

        /// Departure date
        JPanel departurePanel = new JPanel(new BorderLayout());
        departurePanel.setBackground(new Color(8, 20, 140)); // Set background color
        JLabel departureLabel = new JLabel("DEPARTURE");
        departureLabel.setForeground(Color.WHITE); // Set text color to white
        departurePanel.add(departureLabel, BorderLayout.NORTH);

        SqlDateModel departureModel = new SqlDateModel();
        departureModel.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        departureModel.setSelected(true);

        JDatePanelImpl departureDatePanel = new JDatePanelImpl(departureModel, p);
        JDatePickerImpl departureDatePicker = new JDatePickerImpl(departureDatePanel, new DateLabelFormatter());

        // Disable past dates
        departureDatePanel.getModel().addChangeListener(e -> {
            LocalDate selectedDate = LocalDate.of(departureModel.getYear(), departureModel.getMonth() + 1, departureModel.getDay());
            if (selectedDate.isBefore(currentDate)) {
                departureModel.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
            }
        });

        departurePanel.add(departureDatePicker, BorderLayout.SOUTH);
        searchPanel.add(departurePanel);

        searchPanel.add(Box.createHorizontalStrut(20)); // Spacer

        // Search button
        JButton searchButton = new JButton("LET'S GO");
        searchButton.setBackground(Color.WHITE); // Set button background to white
        searchButton.setForeground(new Color(8, 20, 140));
        searchButton.addActionListener(e -> {
            List<Accomodation> filteredAccommodations = new ArrayList<>();
            for (Accomodation accommodation : accommodations) {
                if (accommodation.getAvailability()) {
                    filteredAccommodations.add(accommodation);
                }
            }
            updateTablePanel(filteredAccommodations, tablePanel);
        });
        searchPanel.add(searchButton);

        headerPanel.add(searchPanel, BorderLayout.CENTER);



        // Add header and table to main container panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(headerPanel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(tablePanel), BorderLayout.CENTER);

        mainContainerPanel.add(rightPanel, BorderLayout.CENTER);

        // Filter panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setPreferredSize(new Dimension(200, 0)); // Set preferred width, height is dynamic


        // Price panel
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBackground(new Color(245, 245, 245)); // Set background color
        pricePanel.setBorder(BorderFactory.createLineBorder(new Color(68, 68, 68)));
        pricePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 10));

        JLabel filterLabel = new JLabel("Filter By:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        filterLabel.setForeground(new Color(68, 68, 68)); // Set text color
        pricePanel.add(filterLabel);

        // Add an empty spacer line
        pricePanel.add(Box.createVerticalStrut(10));

        JLabel rangeLabel = new JLabel("Price per Night: (AUD $)");
        rangeLabel.setForeground(new Color(68, 68, 68)); // Set text color
        pricePanel.add(rangeLabel);

        pricePanel.add(Box.createVerticalStrut(10));


        JSlider priceSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
        priceSlider.setPaintTicks(true);
        priceSlider.setPaintLabels(true);
        priceSlider.setMajorTickSpacing(250);
        priceSlider.setMinorTickSpacing(50);
        pricePanel.add(priceSlider);

        filterPanel.add(pricePanel);

        panel.add(filterPanel, BorderLayout.WEST);
        panel.add(mainContainerPanel, BorderLayout.CENTER);


        // Price slider listener
        priceSlider.addChangeListener(e -> {
            int minPrice = priceSlider.getValue();
            int maxPrice = 1000; // This can be adjusted based on requirements

            // Filter accommodations by price
            List<Accomodation> filteredAccommodations = new ArrayList<>();
            for (Accomodation accommodation : accommodations) {
                if (accommodation.getPricePerDay() >= minPrice && accommodation.getPricePerDay() <= maxPrice) {
                    filteredAccommodations.add(accommodation);
                }
            }
            updateTablePanel(filteredAccommodations, tablePanel);
        });

        updateTablePanel(accommodations, tablePanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> redirectToMenu());

        JButton makeBookingButton = new JButton("Make A Booking");
        makeBookingButton.addActionListener(e -> addDeal());

        buttonPanel.add(goBackButton);
        buttonPanel.add(makeBookingButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("All Accommodations", panel);
        tabbedPane.setSelectedComponent(panel);
    }

    private void updateTablePanel(List<Accomodation> accommodations, JPanel tablePanel) {
        tablePanel.removeAll();
        if (accommodations.isEmpty()) {
            tablePanel.add(new JLabel("No accommodations available."));
        } else {
            for (Accomodation accommodation : accommodations) {

                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
                itemPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 1),
                        BorderFactory.createEmptyBorder(10, 5, 10, 5)
                ));

                // Load image
                ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/beds/" + accommodation.getType() + ".jpg")));
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(200, 150, Image.SCALE_SMOOTH); // Resize to fit in panel
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                // Add image
                JLabel imageLabel = new JLabel(resizedIcon);
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                itemPanel.add(imageLabel);

                // Add type and price
                JLabel typePriceLabel = new JLabel("<html><div style='padding-left: 20px; text-align: left;'>" + accommodation.getType() + "<br>From $" + accommodation.getPricePerDay() + "</div></html>");
                typePriceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                itemPanel.add(typePriceLabel);

                // Create panel for availability button
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                // Create availability button
                JButton availabilityButton = new JButton();
                availabilityButton.setText("BOOK NOW");
                availabilityButton.setForeground(Color.WHITE); // White text color
                availabilityButton.setBackground(new Color(0, 20, 137)); // Blue background color
                availabilityButton.addActionListener(e -> addDeal());

                buttonPanel.add(availabilityButton);
                itemPanel.add(buttonPanel);

                tablePanel.add(itemPanel);
            }
        }
        tablePanel.revalidate();
        tablePanel.repaint();
    }


    public void displayAllAccomodations() {
        displayAccommodations(accomodations);
    }


    /**
     * Case 3: Adds a new customer.
     */
    public void addCustomer() {
        JPanel panel = new JPanel(new BorderLayout());

        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/otherheader.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        panel.add(imagePanel, BorderLayout.NORTH);

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Container panel to center input panel
        wrapperPanel.setPreferredSize(new Dimension(600, 400));

        // Register panel
        JPanel registerPanel = new JPanel(new BorderLayout());
        JPanel registerInputPanel = new JPanel(new GridLayout(0, 1, 5, 10));
        JLabel registerLabel = new JLabel("<html><h2>Don't have an account?</h2><p><i>That's okay, you can create one now</i></p></html>");
        registerPanel.add(registerLabel, BorderLayout.NORTH);

        registerInputPanel.add(new JLabel("Customer Name:"));
        JTextField registerNameField = new JTextField();
        registerInputPanel.add(registerNameField);

        registerInputPanel.add(new JLabel("Skiing Level:"));
        String[] options = {"Beginner", "Intermediate", "Expert"};
        JComboBox<String> levelComboBox = new JComboBox<>(options);
        registerInputPanel.add(levelComboBox);

        registerInputPanel.add(Box.createVerticalStrut(5));

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(8, 20, 140)); // Set button background to white
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.addActionListener(e -> {
            String name = registerNameField.getText();
            String skiingLevel = (String) levelComboBox.getSelectedItem();
            if (!name.isEmpty()) {
                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to create a profile for " + name + " with skiing level " + skiingLevel + "?",
                        "Confirm Profile Creation",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (option == JOptionPane.OK_OPTION) {
                    Customer c = new Customer(name, skiingLevel);
                    customers.add(c);

                    JOptionPane.showMessageDialog(
                            null,
                            "Profile created. Taking you back to the main menu.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    removeTab(panel); // Remove the current tab after adding the customer
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Customer registration canceled.",
                            "Canceled",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Customer name cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        registerPanel.add(registerInputPanel, BorderLayout.CENTER);
        registerPanel.add(registerButton, BorderLayout.SOUTH);

        // Center the register panel horizontally
        wrapperPanel.add(registerPanel);

        // Add wrapperPanel to the main container
        panel.add(wrapperPanel, BorderLayout.CENTER);

        // Create the quit button panel
        JPanel quitButtonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning

        // Load and resize the images for the quit and home buttons
        ImageIcon quitIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/exit.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon homeIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/home.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        // Create the quit button with the resized image
        JButton quitButton = new JButton(quitIcon);
        quitButton.setPreferredSize(new Dimension(20, 20));
        quitButton.setMaximumSize(new Dimension(20, 20));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false); // Make the button transparent
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        // Create the home button with the resized image
        JButton homeButton = new JButton(homeIcon);
        homeButton.setPreferredSize(new Dimension(25, 25));
        homeButton.setMaximumSize(new Dimension(25, 25));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false); // Make the button transparent
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> redirectToMenu());

        // Add the home button to the west side of the panel
        quitButtonPanel.add(homeButton, BorderLayout.WEST);
        // Add the quit button to the east side of the panel
        quitButtonPanel.add(quitButton, BorderLayout.EAST);

        // Add the quitButtonPanel to the south of the main panel
        panel.add(quitButtonPanel, BorderLayout.SOUTH);


        tabbedPane.addTab("Add Customer", panel);
        tabbedPane.setSelectedComponent(panel);
    }



    /**
     * Case 4: Lists all customers.
     */
    public void listCustomers() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Header
        JLabel headerLabel = new JLabel("<html><h1>Customers</h1></html>", SwingConstants.CENTER);
        headerPanel.add(headerLabel, BorderLayout.NORTH);

        // Section of text
        JLabel textLabel = new JLabel("<html><div style='text-align: center;'>Our customers are incredibly valuable to Mt Buller Resort.<br>"
                + "We strive to provide the best skiing experience for all our guests.</div></html>", SwingConstants.CENTER);
        headerPanel.add(textLabel, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Customer table
        String[] columnNames = {"Customer ID", "Name", "Skiing Level"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Customer customer : customers) {
                Object[] row = {
                        customer.getCustId(),
                        customer.getName(),
                        customer.getSkiingLevel(),
                };
                model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);


        contentPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        // Buttons
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> redirectToMenu());

        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(e -> addCustomer());

        buttonPanel.add(goBackButton);
        buttonPanel.add(addCustomerButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Customers", panel);
        tabbedPane.setSelectedComponent(panel);
    }

    /**
     * Case 5: Adds a new deal.
     */
    public void addDeal() {
        JPanel panel = new JPanel(new BorderLayout());
        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/otherheader.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        panel.add(imagePanel, BorderLayout.NORTH);

        JPanel loginRegisterContainer = new JPanel(new GridLayout(0, 2, 20, 10)); // Two-column layout for login and register
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Container panel to center input panel
        wrapperPanel.setPreferredSize(new Dimension(600, 400));

        // Left container: Login
        JPanel loginPanel = new JPanel(new BorderLayout());
        JPanel loginInputPanel = new JPanel(new GridLayout(0, 1, 5, 10));
        JLabel loginLabel = new JLabel("<html><h2>Have an account?</h2><p><i>Sign in for faster booking</i></p></html>");
        loginPanel.add(loginLabel, BorderLayout.NORTH);

        loginInputPanel.add(new JLabel("Customer ID:"));
        JTextField loginIdField = new JTextField();
        loginIdField.setPreferredSize(new Dimension(200, 25));
        loginInputPanel.add(loginIdField);
        loginInputPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); // Add a 10-pixel margin on all sides


        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(8, 20, 140)); // Set button background to white
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(e -> {
            String customerIdText = loginIdField.getText();
            if (customerIdText.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Customer ID cannot be empty.", "Login", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int custId = Integer.parseInt(customerIdText);
                Customer customer = findCustomerById(custId);
                if (customer == null) {
                    JOptionPane.showMessageDialog(panel, "No customer found with ID: " + custId, "Login", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                proceedToBooking(panel, custId, customer);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid Customer ID format. Please enter a numeric value.", "Login", JOptionPane.ERROR_MESSAGE);
            }
        });
        loginInputPanel.add(loginButton);


        loginPanel.add(loginInputPanel, BorderLayout.CENTER);
        loginPanel.add(loginButton, BorderLayout.SOUTH);

        // Right container: Register
        JPanel registerPanel = new JPanel(new BorderLayout());
        JPanel registerInputPanel = new JPanel(new GridLayout(0, 1, 5, 10));
        JLabel registerLabel = new JLabel("<html><h2>Don't have an account?</h2><p><i>That's okay, you can create one now</i></p></html>");
        registerPanel.add(registerLabel, BorderLayout.NORTH);

        registerInputPanel.add(new JLabel("Customer Name:"));
        JTextField registerNameField = new JTextField();
        registerInputPanel.add(registerNameField);

        registerInputPanel.add(new JLabel("Skiing Level:"));
        String[] options = {"Beginner", "Intermediate", "Expert"};
        JComboBox<String> levelComboBox = new JComboBox<>(options);
        registerInputPanel.add(levelComboBox);



        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(8, 20, 140)); // Set button background to white
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.addActionListener(e -> {
            String customerName = registerNameField.getText();
            String skiingLevel = (String) levelComboBox.getSelectedItem();
            if (customerName.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Customer name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to create a profile for " + customerName + " with skiing level " + skiingLevel + "?",
                    "Confirm Profile Creation",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                Customer customer = new Customer(customerName, skiingLevel);
                customers.add(customer);

                JOptionPane.showMessageDialog(
                        null,
                        "Profile created. Taking you to booking.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                proceedToBooking(panel, customer.getCustId(), customer);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Customer registration canceled.",
                        "Canceled",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        registerPanel.add(registerInputPanel, BorderLayout.CENTER);
        registerPanel.add(registerButton, BorderLayout.SOUTH);

// Add both panels to the main container
        loginRegisterContainer.add(loginPanel);
        loginRegisterContainer.add(registerPanel);

// Add loginRegisterContainer to the wrapperPanel
        wrapperPanel.add(loginRegisterContainer, BorderLayout.CENTER);

// Add wrapperPanel to the main container
        panel.add(wrapperPanel, BorderLayout.CENTER);

        // Create the quit button panel
        JPanel quitButtonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning

        // Load and resize the images for the quit and home buttons
        ImageIcon quitIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/exit.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon homeIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/home.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        // Create the quit button with the resized image
        JButton quitButton = new JButton(quitIcon);
        quitButton.setPreferredSize(new Dimension(20, 20));
        quitButton.setMaximumSize(new Dimension(20, 20));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false); // Make the button transparent
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        // Create the home button with the resized image
        JButton homeButton = new JButton(homeIcon);
        homeButton.setPreferredSize(new Dimension(25, 25));
        homeButton.setMaximumSize(new Dimension(25, 25));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false); // Make the button transparent
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> redirectToMenu());

        // Add the home button to the west side of the panel
        quitButtonPanel.add(homeButton, BorderLayout.WEST);
        // Add the quit button to the east side of the panel
        quitButtonPanel.add(quitButton, BorderLayout.EAST);

        // Add the quitButtonPanel to the south of the main panel
        panel.add(quitButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Add Deal", panel);
        tabbedPane.setSelectedComponent(panel);
    }


    private void proceedToBooking(JPanel panel, int custId, Customer customer) {
        // Clear the existing content and proceed to the booking section
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/otherheader.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        panel.add(imagePanel, BorderLayout.NORTH);

        // Create header panel similar to searchPanel layout
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel inputContainerPanel = new JPanel();
        inputContainerPanel.setLayout(new BoxLayout(inputContainerPanel, BoxLayout.X_AXIS));
        inputContainerPanel.setBackground(new Color(8, 20, 140));
        inputContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel inputLabelPanel = new JPanel();
        inputLabelPanel.setBackground(new Color(8, 20, 140)); // Set background color
        JLabel inputLabel = new JLabel("BOOK YOUR STAY");
        inputLabel.setForeground(Color.WHITE); // Set text color to white
        inputLabelPanel.add(inputLabel);
        headerPanel.add(inputLabelPanel, BorderLayout.NORTH);

        inputContainerPanel.add(Box.createHorizontalStrut(20)); // Spacer

        JPanel arrivalPanel = new JPanel(new BorderLayout());
        arrivalPanel.setBackground(new Color(8, 20, 140)); // Set background color
        JLabel arrivalLabel = new JLabel("ARRIVAL");
        arrivalLabel.setForeground(Color.WHITE); // Set text color to white
        arrivalPanel.add(arrivalLabel, BorderLayout.NORTH);

        JDatePickerImpl arrivalDatePicker = createDatePicker();
        arrivalPanel.add(arrivalDatePicker, BorderLayout.SOUTH);
        inputContainerPanel.add(arrivalPanel);

        inputContainerPanel.add(Box.createHorizontalStrut(20)); // Spacer

        JPanel departurePanel = new JPanel(new BorderLayout());
        departurePanel.setBackground(new Color(8, 20, 140)); // Set background color
        JLabel departureLabel = new JLabel("DEPARTURE");
        departureLabel.setForeground(Color.WHITE); // Set text color to white
        departurePanel.add(departureLabel, BorderLayout.NORTH);

        JDatePickerImpl departureDatePicker = createDatePicker();
        departurePanel.add(departureDatePicker, BorderLayout.SOUTH);
        inputContainerPanel.add(departurePanel);

        inputContainerPanel.add(Box.createHorizontalStrut(20)); // Space
        headerPanel.add(inputContainerPanel, BorderLayout.CENTER);
        // Create parent panel to wrap input and accommodations panels
        JPanel parentPanel = new JPanel(new BorderLayout());
        parentPanel.add(headerPanel, BorderLayout.NORTH);

        panel.add(parentPanel, BorderLayout.CENTER);

        // Add "LET'S GO" button
        JButton letsGoButton = new JButton("LET'S GO");
        letsGoButton.setBackground(Color.WHITE); // Set button background to white
        letsGoButton.setForeground(new Color(8, 20, 140));
        letsGoButton.addActionListener(e -> {
            try {
                LocalDate arrivalDate = getDateFromPicker(arrivalDatePicker);
                LocalDate departureDate = getDateFromPicker(departureDatePicker);

                // Log the dates to debug
                System.out.println("Arrival Date: " + arrivalDate);
                System.out.println("Departure Date: " + departureDate);

                if (arrivalDate.isAfter(departureDate) || arrivalDate.isEqual(departureDate)) {
                    JOptionPane.showMessageDialog(panel, "Departure date must be after arrival date.", "Add Deal", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int duration = (int) java.time.temporal.ChronoUnit.DAYS.between(arrivalDate, departureDate);
                System.out.println("Duration: " + duration);

                Deal deal = new Deal(custId, arrivalDate, duration);

                JPanel accommodationsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
                for (Accomodation accommodation : accomodations) {
                    JPanel accommodationItemPanel = getjPanel(accommodation, deal, duration, customer);
                    accommodationsPanel.add(accommodationItemPanel);
                }

                parentPanel.add(new JScrollPane(accommodationsPanel), BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();

            } catch (Exception ex) {
                ex.printStackTrace(); // Log the exception stack trace to debug
                JOptionPane.showMessageDialog(panel, "Invalid input. Please enter valid values.", "Add Deal", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Create the quit button panel
        JPanel quitButtonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning

        // Load and resize the images for the quit and home buttons
        ImageIcon quitIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/exit.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon homeIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/home.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        // Create the quit button with the resized image
        JButton quitButton = new JButton(quitIcon);
        quitButton.setPreferredSize(new Dimension(20, 20));
        quitButton.setMaximumSize(new Dimension(20, 20));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false); // Make the button transparent
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        // Create the home button with the resized image
        JButton homeButton = new JButton(homeIcon);
        homeButton.setPreferredSize(new Dimension(25, 25));
        homeButton.setMaximumSize(new Dimension(25, 25));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false); // Make the button transparent
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> redirectToMenu());

        // Add the home button to the west side of the panel
        quitButtonPanel.add(homeButton, BorderLayout.WEST);
        // Add the quit button to the east side of the panel
        quitButtonPanel.add(quitButton, BorderLayout.EAST);

        // Add the quitButtonPanel to the south of the main panel
        panel.add(quitButtonPanel, BorderLayout.SOUTH);
        inputContainerPanel.add(letsGoButton);


        panel.revalidate();
        panel.repaint();
    }


    private JDatePickerImpl createDatePicker() {
        SqlDateModel model = new SqlDateModel();
        LocalDate currentDate = LocalDate.now();
        model.setDate(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        model.setSelected(true);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private LocalDate getDateFromPicker(JDatePickerImpl datePicker) {
        java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
        return selectedDate.toLocalDate();
    }


    static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    public JPanel getjPanel(Accomodation accommodation, Deal deal, double duration, Customer customer) {
        JPanel accommodationItemPanel = new JPanel(new BorderLayout(10, 10));
        accommodationItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Load image
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/beds/" + accommodation.getType() + ".jpg")));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize to fit in panel
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Add image to the left
        JLabel imageLabel = new JLabel(resizedIcon);
        accommodationItemPanel.add(imageLabel, BorderLayout.WEST);

        // Panel for text
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setLayout(new GridLayout(0,1,0,0));

        JLabel titleLabel = new JLabel(accommodation.getType());
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(titleLabel);

        JLabel priceLabel = new JLabel("$" + accommodation.getPricePerDay());
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(priceLabel);

        accommodationItemPanel.add(textPanel, BorderLayout.CENTER);

        // Select Button Panel
        JPanel selectPanel = new JPanel();
        selectPanel.setBorder(BorderFactory.createEmptyBorder(0, -20, 0, 0));

        // Add Select button to the right
        JButton selectButton = new JButton("BOOK NOW");
        selectButton.setForeground(Color.WHITE); // White text color
        selectButton.setBackground(new Color(0, 20, 137)); // Blue background color
        selectButton.setFocusPainted(false);
        if (!accommodation.getAvailability()) {
            selectButton.setEnabled(false);
            selectButton.setBackground(Color.GRAY); // Gray background color for disabled state
        }

        selectButton.addActionListener(e -> {
            if (accommodation.getAvailability()) {
                accommodation.setAvailability(false);
                deal.setType(accommodation.getType()); // Set the type of accommodation in the Deal object
                double totalCost = accommodation.getPricePerDay() * duration;
                deal.setTotalCost(totalCost);

                JOptionPane.showMessageDialog(
                        null,
                        "Accommodation selected: " + accommodation.getType() + "\nTotal cost: $" + totalCost,
                        "Accommodation Selected",
                        JOptionPane.INFORMATION_MESSAGE
                );

                removeTab(accommodationItemPanel.getParent().getParent().getParent()); // Remove the accommodation selection tab
                showExtrasPage(deal, customer); // Proceed to showExtrasPage
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Accommodation not available.",
                        "Select Accommodation",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        selectPanel.add(selectButton);
        textPanel.add(selectPanel, BorderLayout.SOUTH);

        return accommodationItemPanel;
    }

    /**
     * Shows the extras page where customers can add lift passes or skiing lessons.
     * @param deal The current deal.
     * @param customer The current customer.
     */

    private void showExtrasPage(Deal deal, Customer customer) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel parentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        parentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Set a 10-pixel margin on all sides
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 50));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JPanel infoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/otherheader.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);


        // "More lift pass information" button
        JButton liftPassInfoButton = new JButton("More lift pass information");
        liftPassInfoButton.addActionListener(e -> showLiftPassInfo());

        JButton lessonInfoButton = new JButton("More skiing lesson information");
        lessonInfoButton.addActionListener(e -> showLessonInfo());

        infoButtonPanel.add(liftPassInfoButton);
        infoButtonPanel.add(lessonInfoButton);

        // Create a header panel with headerLabel at the top and infoButtonPanel at the bottom
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(imagePanel, BorderLayout.NORTH);
        headerPanel.add(infoButtonPanel, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);


        // Input fields
        JLabel infoLabel = new JLabel("<html><h2>Add Extras!</h2><p><i>Purchase lift or lessons now, or access via the menu later.</i></p></html>");


        inputPanel.add(new JLabel("Purchase lessons?"));
        JComboBox<String> skiingChoiceComboBox = new JComboBox<>(new String[]{"No", "Yes"});
        skiingChoiceComboBox.setPreferredSize(new Dimension(100, 24)); // Set preferred size
        inputPanel.add(skiingChoiceComboBox);

        JLabel numberOfLessonsLabel = new JLabel("Number of lessons:");
        JTextField numberOfLessonsField = new JTextField();
        numberOfLessonsField.setPreferredSize(new Dimension(100, 24)); // Set preferred size

        JLabel lessonCostLabel = new JLabel("Cost for lessons: $0.00");

        // Initially hide the number of lessons input and cost label
        numberOfLessonsLabel.setVisible(false);
        numberOfLessonsField.setVisible(false);
        lessonCostLabel.setVisible(false);

        inputPanel.add(numberOfLessonsLabel);
        inputPanel.add(numberOfLessonsField);

        inputPanel.add(new JLabel("")); // Empty label to fill grid space
        inputPanel.add(lessonCostLabel);

        inputPanel.add(new JLabel("Select a lift pass:"));
        JComboBox<String> liftPassChoiceComboBox = new JComboBox<>(new String[]{
                "No Lift Pass", "Full day lift pass ($26)", "5-day lift pass ($117)", "Season lift pass ($200)"
        });
        liftPassChoiceComboBox.setPreferredSize(new Dimension(200, 24)); // Set preferred size
        inputPanel.add(liftPassChoiceComboBox);



        // Update total cost dynamically
        numberOfLessonsField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateCosts(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateCosts(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateCosts(); }

            private void updateCosts() {
                try {
                    int numberOfLessons = Integer.parseInt(numberOfLessonsField.getText());
                    double lessonFee = getLessonFee(customer);
                    double totalLessonCost = lessonFee * numberOfLessons;
                    String selectedLiftPass = (String) liftPassChoiceComboBox.getSelectedItem();
                    Objects.requireNonNull(selectedLiftPass);
                    lessonCostLabel.setText(String.format("Cost for lessons: $%.2f", totalLessonCost));
                } catch (NumberFormatException ex) {
                    lessonCostLabel.setText("Cost for lessons: $0.00");
                }
            }
        });

        // Add action listener to skiing choice combo box
        skiingChoiceComboBox.addActionListener(e -> {
            boolean lessonsSelected = Objects.equals(skiingChoiceComboBox.getSelectedItem(), "Yes");
            numberOfLessonsLabel.setVisible(lessonsSelected);
            numberOfLessonsField.setVisible(lessonsSelected);
            lessonCostLabel.setVisible(lessonsSelected);
            panel.revalidate();
            panel.repaint();
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(infoLabel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Create payment panel for entering credit card details
        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10)); // Add a 10-pixel margin on all sides
        JPanel paymentInputPanel = new JPanel(new GridLayout(0, 1, 5, 10));
        paymentInputPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        JLabel paymentLabel = new JLabel("<html><h2>Payment Information</h2><p><i>Enter your credit card details</i></p></html>");
        paymentPanel.add(paymentLabel, BorderLayout.NORTH);

        paymentInputPanel.add(new JLabel("Credit Card Number:"));
        JTextField cardNumberField = new JTextField();
        paymentInputPanel.add(cardNumberField);

        // Create a panel for CVC and Expiry Date
        JPanel cvcExpiryPanel = new JPanel(new GridLayout(1, 2, 5, 10));
        cvcExpiryPanel.add(new JLabel("CVC:"));
        JTextField cvcField = new JTextField();
        cvcExpiryPanel.add(cvcField);
        cvcExpiryPanel.add(new JLabel("Expiry Date:"));
        JTextField expiryDateField = new JTextField();
        cvcExpiryPanel.add(expiryDateField);

// Add cvcExpiryPanel to paymentInputPanel
        paymentInputPanel.add(cvcExpiryPanel);


        JButton finishBookingButton = new JButton("FINISH BOOKING");
        finishBookingButton.setBackground(new Color(8, 20, 140)); // Set button background to white
        finishBookingButton.setForeground(Color.WHITE);
        finishBookingButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        finishBookingButton.setOpaque(true);
        finishBookingButton.setBorderPainted(false);
        finishBookingButton.addActionListener(e -> {
            if (Objects.equals(skiingChoiceComboBox.getSelectedItem(), "Yes")) {
                double lessonFee = getLessonFee(customer);
                int numberOfLessons = Integer.parseInt(numberOfLessonsField.getText());
                deal.addLessons(numberOfLessons, lessonFee);
                deal.setTotalCost(deal.getTotalCost() + lessonFee * numberOfLessons);
            }

            String selectedLiftPass = (String) liftPassChoiceComboBox.getSelectedItem();
            switch (Objects.requireNonNull(selectedLiftPass)) {
                case "Full day lift pass ($26)":
                    deal.addLiftPass(26, "Full day");
                    deal.setTotalCost(deal.getTotalCost() + 26);
                    break;
                case "5-day lift pass (10% discount)":
                    double discount = 0.1;
                    double discountedPrice = 26 * 5 * (1 - discount);
                    deal.addLiftPass(discountedPrice, "5-day");
                    deal.setTotalCost(deal.getTotalCost() + discountedPrice);
                    break;
                case "Season lift pass ($200)":
                    deal.addLiftPass(200, "Season");
                    deal.setTotalCost(deal.getTotalCost() + 200);
                    break;
                default:
                    break;
            }

            // Add the deal to the deals list
            deals.add(deal);

            JOptionPane.showMessageDialog(
                    null,
                    "<html><div style='text-align: center;'>Thank you for booking with us!<br>" +
                            "To view your invoice, visit our 'List Packages' page.<br>" +
                            "We hope you have a snow-tastic time on the slopes!<br><br>" +
                            "Total Cost: $" + deal.getTotalCost() + "</div></html>",
                    "Booking Details",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // Redirect to menu
            redirectToMenu();
        });

        paymentPanel.add(paymentInputPanel, BorderLayout.CENTER);
        paymentPanel.add(finishBookingButton, BorderLayout.SOUTH);


        parentPanel.add(mainPanel);
        parentPanel.add(paymentPanel);
        panel.add(parentPanel, BorderLayout.CENTER);


        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            removeTab(panel); // Remove the current tab
            tabbedPane.setSelectedIndex(0); // Return to the menu tab
        });

        buttonPanel.add(goBackButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Add Extras", panel);
        tabbedPane.setSelectedComponent(panel);
    }
    /**
     * Shows information about skiing lessons.
     */
    private void showLessonInfo() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Lesson text
        JLabel lessonText = new JLabel("<html><h1>Level up with a lesson from Mt Buller's Ski and Snowboard School.</h1>" +
                "<p>We have some of the best instructors from across the world ready to help you improve your skills, gain confidence and explore the wonders of Mt Buller.</p>" +
                "<p><strong>Lesson Fee Table</strong></p></html>", SwingConstants.CENTER);
        headerPanel.add(lessonText, BorderLayout.NORTH);

        // Lesson fee table
        String[] columnNames = {"Skiing Level", "Lesson Fee"};
        Object[][] data = {
                {"Beginner", "$25"},
                {"Intermediate", "$20"},
                {"Expert", "$15"}
        };
        JTable lessonFeeTable = new JTable(data, columnNames);
        lessonFeeTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(lessonFeeTable);
        headerPanel.add(tableScrollPane, BorderLayout.CENTER);
        headerPanel.setPreferredSize(new Dimension(400, 300));

        // Display the header panel in a dialog box
        JOptionPane.showMessageDialog(null, headerPanel, "Lesson Information", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Shows information about lift passes.
     */
    private void showLiftPassInfo() {
        JPanel panel = new JPanel(new BorderLayout());


        // Main content panel
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Full day lift pass panel
        JPanel fullDayPanel = createLiftPassInfoPanel("FULL DAY", "$26",
                "Experience a full day of adventure at Mt Buller with our<br>Full Day lift pass. This pass gives you unlimited access<br>to all the ski lifts from morning till evening."
        );
        contentPanel.add(fullDayPanel);

        // 5-day lift pass panel
        JPanel fiveDayPanel = createLiftPassInfoPanel("5-DAY (10% OFF)", "$117",
                "Make the most of your visit with our 5-Day lift pass and<br>save 10%! This pass provides five consecutive days of unlimited access."
        );
        contentPanel.add(fiveDayPanel);

        // Season lift pass panel
        JPanel seasonPanel = createLiftPassInfoPanel("SEASON", "$200",
                "For the ultimate skiing and snowboarding experience,<br>choose our Season lift pass. This pass offers unrestricted access<br>to all lifts throughout the entire ski season."
        );
        contentPanel.add(seasonPanel);

        panel.add(contentPanel, BorderLayout.CENTER);

        // Create a scroll pane wrapping the panel
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Display the scroll pane in a JOptionPane
        JOptionPane.showMessageDialog(null, scrollPane, "Lift Pass Information", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Creates a panel with information about a specific lift pass.
     * @param title The title of the lift pass.
     * @param price The price of the lift pass.
     * @param description A description of the lift pass.
     * @return A JPanel containing the lift pass information.
     */
    private JPanel createLiftPassInfoPanel(String title, String price, String description) {
        JPanel liftPassPanel = new JPanel(new BorderLayout(10, 10));

        // Image
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/skiing.jpg")));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize to fit in panel
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        liftPassPanel.add(imageLabel, BorderLayout.WEST);

        // Description
        JLabel descriptionLabel = new JLabel("<html><b>" + title + "</b><br><b>Price: " + price + "</b><br>" + description + "</html>", SwingConstants.LEFT);
        liftPassPanel.add(descriptionLabel, BorderLayout.CENTER);

        return liftPassPanel;
    }

    /**
     * Gets the lesson fee based on the customer's skiing level.
     * @param customer The customer for whom the lesson fee is to be determined.
     * @return The lesson fee.
     */

    private double getLessonFee(Customer customer) {
        return switch (customer.getSkiingLevel().toLowerCase()) {
            case "beginner" -> 25;
            case "intermediate" -> 20;
            case "expert" -> 15;
            default -> 0;
        };
    }

    /**
     * Redirects the user to the menu.
     */

    private void redirectToMenu() {
        for (int i = tabbedPane.getTabCount() - 1; i >= 0; i--) {
            if (!tabbedPane.getTitleAt(i).equals("Menu")) {
                tabbedPane.remove(i);
            }
        }
        tabbedPane.setSelectedIndex(0); // Return to the menu tab
    }




    /**
     * Finds a customer by ID.
     * @param custId The ID of the customer to find
     * @return The customer with the specified ID, or null if not found
     */
    public Customer findCustomerById(int custId) {
        for (Customer customer : customers) {
            if (customer.getCustId() == custId) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Finds a deal by customer ID.
     * @param custId The customer ID associated with the deal to find
     * @return The deal associated with the specified customer ID, or null if not found
     */
    public Deal findDealByCustomerId(int custId) {
        for (Deal deal : deals) {
            if (deal.getCustId() == custId) {
                return deal; // Return the deal if found
            }
        }
        return null; // Return null if no deal is found for the given customer ID
    }

    /**
     * Case 6: Lists all deals. Uses JTable.
     */
    public void listDeals() {
        String custIdInput = JOptionPane.showInputDialog(null, "Enter Customer ID:", "Customer ID", JOptionPane.QUESTION_MESSAGE);
        if (custIdInput == null || custIdInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer ID input canceled or empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int custId = Integer.parseInt(custIdInput);
            Customer customer = findCustomerById(custId);
            if (customer == null) {
                JOptionPane.showMessageDialog(null, "No customer found with ID: " + custId, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel panel = new JPanel(new BorderLayout(10, 10));
            JPanel headerPanel = new JPanel(new BorderLayout());
            JPanel contentPanel = new JPanel(new BorderLayout());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

            // Header
            JLabel headerLabel = new JLabel("<html><h1>" + customer.getName() + "'s Bookings</h1></html>", SwingConstants.CENTER);
            headerPanel.add(headerLabel, BorderLayout.NORTH);

            // Section of text
            JLabel textLabel = new JLabel("<html><div style='text-align: center;'>We are pleased to inform you that your reservation request has been received and confirmed. Thank you!</div></html>", SwingConstants.CENTER);
            headerPanel.add(textLabel, BorderLayout.CENTER);

            panel.add(headerPanel, BorderLayout.NORTH);

            // Deals table
            String[] columnNames = {"Deal ID", "Customer ID", "Check-in", "Duration (days)", "Accommodation Type", "Lift Pass", "Skiing Lessons", "Total Cost"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            boolean hasDeals = false;
            for (Deal deal : deals) {
                if (deal.getCustId() == custId) {
                    Object[] row = {
                            deal.getDealId(),
                            deal.getCustId(),
                            deal.getDate(),
                            deal.getDuration(),
                            deal.getType(),
                            deal.hasLiftPass() ? deal.getType(): "None purchased", // Modified line
                            deal.getNumLessons(),
                            deal.getTotalCost()
                    };
                    model.addRow(row);
                    hasDeals = true;
                }
            }

            if (!hasDeals) {
                JOptionPane.showMessageDialog(null, "No deals available for the specified customer.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            contentPanel.add(scrollPane, BorderLayout.CENTER);
            panel.add(contentPanel, BorderLayout.CENTER);

            // Go Back button
            JButton goBackButton = new JButton("Go Back");
            goBackButton.addActionListener(e -> redirectToMenu());

            buttonPanel.add(goBackButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            // Add the panel to the tabbed pane
            tabbedPane.addTab("Deals for " + customer.getName(), panel);
            tabbedPane.setSelectedComponent(panel);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Customer ID format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Searches for an accommodation by accommodation number.
     *
     * @param accomodationNo The accommodation number to search for
     * @return The accommodation with the specified number, or null if not found
     */
    public Accomodation searchAccomodationsByAccomodationNo(int accomodationNo) {
        for (Accomodation a : accomodations) {
            if (a.getAccomodationNo() == accomodationNo)
                return a;
        }
        return null;
    }



    /**
     * Case 7: Adds a lift pass to a deal.
     */
    public void addPassToDeal() {
        JPanel panel = new JPanel(new BorderLayout());

        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/passbanner.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        panel.add(imagePanel, BorderLayout.NORTH);

        // Wrapper panel for content with GridBagLayout
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 10, 10); // Add desired insets

        // Main content panel for the first column (60% width) with text
        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setBackground(Color.WHITE);
        textContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel infoLabel = new JLabel("<html><h2>FULL DAY</h2>"
                + "<p>Experience a full day of adventure at Mt Buller with our Full Day lift pass. Perfect for visitors who want to make"
                + "the most of their time on the slopes, this pass gives you unlimited access to all the ski lifts from morning till evening.</p>"
                +"<h2>5-DAY</h2>"
                + "<p>Make the most of your visit with our 5-Day lift pass and save 10%! This pass offers great value for avid skiers and"
                + "snowboarders, providing five consecutive days of unlimited access to all lifts.</p>"
                +"<h2>SEASON</h2>"
                + "<p>For the ultimate skiing and snowboarding experience, choose our Season lift pass. This pass offers unrestricted"
                + " access to all lifts throughout the entire ski season</p>"
                + "<p><a href='#'>View our Cancellation Policy here</a></p></html>");
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.WHITE);

        textContainer.add(infoLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6; // 60% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;
        wrapperPanel.add(textContainer, gbc);

        // Different content panel for the second column (40% width)
        JPanel blueContainer = new JPanel();
        blueContainer.setLayout(new BoxLayout(blueContainer, BoxLayout.Y_AXIS));
        blueContainer.setBackground(new Color(8, 20, 140));
        blueContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("<html><h1 style='color:white;'><b><i>LIFT PASSES</i></b></h1></html>");
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton goButton = new JButton("LET'S GO");
        goButton.setBackground(Color.WHITE); // Set button background to white
        goButton.setForeground(new Color(8, 20, 140));
        goButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        goButton.setOpaque(true);
        goButton.setBorderPainted(false);

        PromptTextField custIdField = new PromptTextField("Enter your Customer ID");
        custIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JComboBox<String> liftPassChoiceComboBox = new JComboBox<>(new String[]{
                "No Lift Pass", "Full day lift pass ($26)", "5-day lift pass ($117)", "Season lift pass ($200)"
        });
        liftPassChoiceComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        blueContainer.add(headerLabel);
        blueContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        blueContainer.add(custIdField);
        blueContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        blueContainer.add(liftPassChoiceComboBox);
        blueContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        blueContainer.add(goButton);

// Container to hold the image and blueContainer
        JPanel imageAndBlueContainer = new JPanel();
        imageAndBlueContainer.setLayout(new BoxLayout(imageAndBlueContainer, BoxLayout.Y_AXIS));

// Add the image at the top of the container
        ImageIcon imageIcon = new ImageIcon("images/lessontable.jpg");
        imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

// Add the image and blueContainer to the new container
        imageAndBlueContainer.add(imageLabel);
        imageAndBlueContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        imageAndBlueContainer.add(blueContainer);

// Update GridBagConstraints and add the new container to wrapperPanel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // 40% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;
        wrapperPanel.add(imageAndBlueContainer, gbc);

// Add the wrapper panel to the main panel with border
        JPanel wrapperWithInsets = new JPanel(new BorderLayout());
        wrapperWithInsets.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add desired insets
        wrapperWithInsets.add(wrapperPanel, BorderLayout.CENTER);

        panel.add(wrapperWithInsets, BorderLayout.CENTER);


        // Add action listener for the button
        goButton.addActionListener(e -> {
            String custIdStr = custIdField.getText();
            String selectedLiftPass = (String) liftPassChoiceComboBox.getSelectedItem();
            if (custIdStr.isEmpty() || selectedLiftPass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int custId = Integer.parseInt(custIdStr);

                Customer customer = findCustomerById(custId);
                if (customer == null) {
                    JOptionPane.showMessageDialog(null, "No customer found with ID: " + custId, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Deal deal = findDealByCustomerId(custId);
                if (deal == null) {
                    JOptionPane.showMessageDialog(null, "No deal found for customer with ID: " + custId, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double cost = getLiftPassCost(selectedLiftPass);

                deal.addLiftPass(cost, selectedLiftPass);
                deal.setTotalCost(deal.getTotalCost() + cost);
                JOptionPane.showMessageDialog(null, selectedLiftPass + " lift pass added.", "Lift Pass", JOptionPane.INFORMATION_MESSAGE);
                redirectToMenu(); // Redirect to the main menu
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Create the quit button panel
        JPanel quitButtonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning

        // Load and resize the images for the quit and home buttons
        ImageIcon quitIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/exit.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon homeIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/home.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        // Create the quit button with the resized image
        JButton quitButton = new JButton(quitIcon);
        quitButton.setPreferredSize(new Dimension(20, 20));
        quitButton.setMaximumSize(new Dimension(20, 20));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false); // Make the button transparent
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        // Create the home button with the resized image
        JButton homeButton = new JButton(homeIcon);
        homeButton.setPreferredSize(new Dimension(25, 25));
        homeButton.setMaximumSize(new Dimension(25, 25));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false); // Make the button transparent
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> redirectToMenu());

        // Add the home button to the west side of the panel
        quitButtonPanel.add(homeButton, BorderLayout.WEST);
        // Add the quit button to the east side of the panel
        quitButtonPanel.add(quitButton, BorderLayout.EAST);

        // Add the quitButtonPanel to the south of the main panel
        panel.add(quitButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Add Pass", panel);
        tabbedPane.setSelectedComponent(panel);
    }
    /**
     * Gets lift Pass cost
     */
    private double getLiftPassCost(String selectedLiftPass) {
        return switch (selectedLiftPass) {
            case "Full day lift pass ($26)" -> 26.0;
            case "5-day lift pass ($117)" -> 117.0;
            case "Season lift pass ($200)" -> 200.0;
            default -> 0.0;
        };
    }


    /**
     * Case 8: Adds skiing lessons to a deal.
     */

    //First "lesson info page"
    public void lessonPanel(){
        // Create the main panel with BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        //wrapper for information
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(new EmptyBorder(10, 30, 10, 30)); // Add 10 pixels of padding on all sides


        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/lessonbanner.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 180, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        panel.add(imagePanel, BorderLayout.NORTH);

        // Create the text panel at the NORTH
        JLabel infoLabel = new JLabel("<html>Level up with a lesson from Mt Buller's Ski and Snowboard School. "
                + "We have some of the best instructors from across the world ready to help you improve your skills, "
                + "gain confidence and explore the wonders of Mt Buller. Check out below to discover which type of lesson is best for you.</html>");
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font to plain
        infoLabel.setForeground(new Color(55, 65, 81)); // Set text color
        wrapperPanel.add(infoLabel, BorderLayout.NORTH);


        // Create the container panel for lessons
        JPanel lessonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        lessonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Add individual lesson panels
        lessonsPanel.add(createLessonPanel("PRIVATE LESSONS", "Enjoy the ultimate Mt Buller experience with personalised instruction for all ages.",
                "https://www.mtbuller.com.au/hs-fs/hubfs/Winter%20Images/MBJM_210610_6659.jpg?width=2880&height=1920&name=MBJM_210610_6659.jpg"));
        lessonsPanel.add(createLessonPanel("ADULT GROUP LESSONS", "Join an Adult Group Lesson that caters for your level. Ages 15+.",
                "https://www.mtbuller.com.au/hs-fs/hubfs/Winter%20Images/MBAR_120717_510.jpg?width=2464&height=1640&name=MBAR_120717_510.jpg"));
        lessonsPanel.add(createLessonPanel("CHILD GROUP LESSONS", "Watch your kids learn and gain confidence while having fun. Ages 3-14.",
                "https://www.mtbuller.com.au/hs-fs/hubfs/Winter%20Images/MBTH210915_156.jpg?width=4128&height=2752&name=MBTH210915_156.jpg"));


        // Add lessonsPanel to mainPanel at CENTER
        wrapperPanel.add(lessonsPanel, BorderLayout.CENTER);
        panel.add(wrapperPanel, BorderLayout.CENTER);

        // Create the quit button panel
        JPanel quitButtonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning

        // Load and resize the images for the quit and home buttons
        ImageIcon quitIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/exit.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon homeIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/home.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        // Create the quit button with the resized image
        JButton quitButton = new JButton(quitIcon);
        quitButton.setPreferredSize(new Dimension(20, 20));
        quitButton.setMaximumSize(new Dimension(20, 20));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false); // Make the button transparent
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        // Create the home button with the resized image
        JButton homeButton = new JButton(homeIcon);
        homeButton.setPreferredSize(new Dimension(25, 25));
        homeButton.setMaximumSize(new Dimension(25, 25));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false); // Make the button transparent
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> createMenuPanel());

        // Add the home button to the west side of the panel
        quitButtonPanel.add(homeButton, BorderLayout.WEST);
        // Add the quit button to the east side of the panel
        quitButtonPanel.add(quitButton, BorderLayout.EAST);

        // Add the quitButtonPanel to the south of the main panel
        panel.add(quitButtonPanel, BorderLayout.SOUTH);

        // Add main panel to frame and make it visible
        tabbedPane.addTab("Lesson Information", panel);
        tabbedPane.setSelectedComponent(panel);
    }
    /**
     * Creates a lesson panel with the given title, description, and image URL.
     */
    private JPanel createLessonPanel(String title, String description, String imageUrl) {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);


        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16)); // Set font to bold and italic
        titleLabel.setForeground(new Color(8, 20, 140)); // Set text color to white
        titleLabel.setOpaque(false); // Make the label non-opaque to customize painting
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Set font to plain
        descLabel.setForeground(new Color(55, 65, 81)); // Set text color
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Load and resize image from URL
        ImageIcon imageIcon = null;
        try {
            URL url = new URL(imageUrl);
            imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage(); // transform it
            Image newimg = image.getScaledInstance(150, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            imageIcon = new ImageIcon(newimg);  // transform it back
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton("READ MORE");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

// Set the button background color
        button.setBackground(new Color(8, 20, 140));

// Set the button text color
        button.setForeground(Color.WHITE);

// Set the button text to bold and italic
        button.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));

// Make sure the background color is displayed
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Add action listener to call addLessonToDeal() on button click
        button.addActionListener(e -> addLessonToDeal());


        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(descLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(imageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(button);

        return panel;
    }
    /**
     * A JTextField that displays prompt text when the field is empty and not focused.
     */
    public class PromptTextField extends JTextField implements FocusListener {
        private final String prompt;

        public PromptTextField(String prompt) {
            this.prompt = prompt;
            addFocusListener(this);
            setPromptText();
        }
        /**
         * Sets the prompt text when the field is empty.
         */
        private void setPromptText() {
            if (getText().isEmpty()) {
                setText(prompt);
                setForeground(Color.GRAY);
            }
        }
        /**
         * Removes the prompt text when the field is focused.
         */
        private void removePromptText() {
            if (getText().equals(prompt)) {
                setText("");
                setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            removePromptText();
        }

        @Override
        public void focusLost(FocusEvent e) {
            setPromptText();
        }
    }
    /**
     * Adds a lesson to the current deal.
     */
    public void addLessonToDeal() {
        JPanel panel = new JPanel(new BorderLayout());

        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/privlesson.jpg"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(800, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        panel.add(imagePanel, BorderLayout.NORTH);

        // Wrapper panel for content with GridBagLayout
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add desired insets

        // Main content panel for the first column (60% width) with text
        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setBackground(Color.WHITE);
        textContainer.setBorder(new EmptyBorder(10, 0, 10, 10));

        JLabel infoLabel = new JLabel("<html><h2>WHERE DO I MEET MY PRIVATE INSTRUCTOR?</h2>"
                + "<p>All private lessons meet outside the Ski & Snowboard School building near the base of the ABOM Express Chairlift. "
                + "<h3>LESSON FEES</h3>"
                + "Please inpect the table below.</p>"
                + "<p><a href='#'>View more info on skiing levels</a></p></html>");
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.WHITE);

        textContainer.add(infoLabel);

        // Adding the lesson fee table
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
        String[] columnNames = {"Skiing Level", "Lesson Fee"};
        Object[][] data = {
                {"Beginner", "$25"},
                {"Intermediate", "$20"},
                {"Expert", "$15"}
        };
        JTable lessonFeeTable = new JTable(data, columnNames);
        lessonFeeTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(lessonFeeTable);

        tableWrapper.add(tableScrollPane);
        textContainer.add(tableWrapper);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6; // 60% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;
        wrapperPanel.add(textContainer, gbc);

        // Different content panel for the second column (40% width)
        JPanel blueContainer = new JPanel();
        blueContainer.setLayout(new BoxLayout(blueContainer, BoxLayout.Y_AXIS));
        blueContainer.setBackground(new Color(8, 20, 140));
        blueContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("<html><h1 style='color:white;'><b><i>SKI & SNOWBOARD SCHOOL</i></b></h1></html>");
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton goButton = new JButton("LET'S GO");
        goButton.setBackground(Color.WHITE); // Set button background to white
        goButton.setForeground(new Color(8, 20, 140));
        goButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        goButton.setOpaque(true);
        goButton.setBorderPainted(false);

        PromptTextField custIdField = new PromptTextField("Enter your Customer ID");
        custIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        PromptTextField lessonsField = new PromptTextField("Number of lessons");
        lessonsField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        blueContainer.add(headerLabel);
        blueContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        blueContainer.add(custIdField);
        blueContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        blueContainer.add(lessonsField);
        blueContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        blueContainer.add(goButton);

// Container to hold the image and blueContainer
        JPanel imageAndBlueContainer = new JPanel();
        imageAndBlueContainer.setLayout(new BoxLayout(imageAndBlueContainer, BoxLayout.Y_AXIS));

// Add the image at the top of the container
        ImageIcon imageIcon = new ImageIcon("images/lessontable.jpg");
        imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

// Add the image and blueContainer to the new container
        imageAndBlueContainer.add(imageLabel);
        imageAndBlueContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        imageAndBlueContainer.add(blueContainer);

// Update GridBagConstraints and add the new container to wrapperPanel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // 40% of the width
        gbc.weighty = 1.0; // Allow vertical resizing
        gbc.fill = GridBagConstraints.BOTH;
        wrapperPanel.add(imageAndBlueContainer, gbc);

// Add the wrapper panel to the main panel with border
        JPanel wrapperWithInsets = new JPanel(new BorderLayout());
        wrapperWithInsets.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add desired insets
        wrapperWithInsets.add(wrapperPanel, BorderLayout.CENTER);

        panel.add(wrapperWithInsets, BorderLayout.CENTER);


        // Add action listener for the button
        goButton.addActionListener(e -> {
            String custIdStr = custIdField.getText();
            String lessonsStr = lessonsField.getText();
            if (custIdStr.isEmpty() || lessonsStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int custId = Integer.parseInt(custIdStr);
                int numberOfLessons = Integer.parseInt(lessonsStr);

                Customer customer = findCustomerById(custId);
                if (customer == null) {
                    JOptionPane.showMessageDialog(null, "No customer found with ID: " + custId, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Deal deal = findDealByCustomerId(custId);
                if (deal == null) {
                    JOptionPane.showMessageDialog(null, "No deal found for customer with ID: " + custId, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double lessonFee = getLessonFee(customer);
                deal.addLessons(numberOfLessons, lessonFee);
                deal.setTotalCost(deal.getTotalCost() + lessonFee * numberOfLessons);
                JOptionPane.showMessageDialog(null, "Lessons added to the deal.", "Success", JOptionPane.INFORMATION_MESSAGE);
                redirectToMenu(); // Redirect to the main menu
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Create the quit button panel
        JPanel quitButtonPanel = new JPanel(new BorderLayout()); // Use BorderLayout for positioning

        // Load and resize the images for the quit and home buttons
        ImageIcon quitIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/exit.jpg"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon homeIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/home.jpg"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        // Create the quit button with the resized image
        JButton quitButton = new JButton(quitIcon);
        quitButton.setPreferredSize(new Dimension(20, 20));
        quitButton.setMaximumSize(new Dimension(20, 20));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false); // Make the button transparent
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        // Create the home button with the resized image
        JButton homeButton = new JButton(homeIcon);
        homeButton.setPreferredSize(new Dimension(25, 25));
        homeButton.setMaximumSize(new Dimension(25, 25));
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false); // Make the button transparent
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> redirectToMenu());

        // Add the home button to the west side of the panel
        quitButtonPanel.add(homeButton, BorderLayout.WEST);
        // Add the quit button to the east side of the panel
        quitButtonPanel.add(quitButton, BorderLayout.EAST);

        // Add the quitButtonPanel to the south of the main panel
        panel.add(quitButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Add Lessons", panel);
        tabbedPane.setSelectedComponent(panel);

    }




    /**
     * Case 9: Saves deals to a file.
     */
    public void saveDeals() {
        try {
            FileOutputStream fos = new FileOutputStream("deals.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Deal p : deals) {
                oos.writeObject(p);
            }
            oos.close();
            fos.close();
            JOptionPane.showMessageDialog(null, "File has been saved as deals.dat", "Save Deals", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Case 10: Reads deals from a file.
     */
    public void readDeals() {
        deals.clear();

        try (FileInputStream fis = new FileInputStream("deals.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            StringBuilder sb = new StringBuilder();
            sb.append("Here is deals.dat which you have saved:\n\n");

            while (true) {
                try {
                    Object object = ois.readObject();
                    Deal p = (Deal) object;
                    int accommodationNo = p.getAccomodationNo();
                    Accomodation a = searchAccomodationsByAccomodationNo(accommodationNo);
                    if (a != null) {
                        a.setAvailability(false);
                    }
                    deals.add(p);
                    sb.append(p).append("\n");
                } catch (EOFException eof) {
                    break;
                }
            }

            showMessage(sb.toString(), "Read Deals");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * The main method that launches the application.
     * It uses SwingUtilities.invokeLater to ensure that the GUI is created and updated on the Event Dispatch Thread (EDT).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MtBullerResort().createAndShowGUI());
    }

    }


