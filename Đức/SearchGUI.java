import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchGUI extends JFrame {
    private SearchEngine engine;
    private JTextArea websiteList;
    private JTextField keyword1Field;
    private JTextField keyword2Field;
    private JTextField keyword3Field;
    private JTextArea resultArea;
    
    public SearchGUI() {
        this.engine = new SearchEngine();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Tìm kiếm tin trên internet");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Tìm kiếm tin trên internet", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        centerPanel.add(createWebsitePanel());
        centerPanel.add(createSearchPanel());
        centerPanel.add(createResultPanel());
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        loadWebsites();
        setLocationRelativeTo(null);
    }
    
    private JPanel createWebsitePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JLabel label = new JLabel("Danh sách web");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panel.add(label, BorderLayout.NORTH);
        
        websiteList = new JTextArea();
        websiteList.setEditable(false);
        websiteList.setFont(new Font("Arial", Font.PLAIN, 12));
        websiteList.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(websiteList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        JTextField urlField = new JTextField();
        urlField.setFont(new Font("Arial", Font.PLAIN, 12));
        urlField.setToolTipText("Nhập link website");
        
        JButton addButton = new JButton("Thêm");
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setPreferredSize(new Dimension(80, 30));
        addButton.addActionListener(e -> {
            String url = urlField.getText().trim();
            if (!url.isEmpty()) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }
                engine.addWebsite(url);
                loadWebsites();
                urlField.setText("");
                JOptionPane.showMessageDialog(this, "Đã thêm: " + url);
            }
        });
        
        bottomPanel.add(urlField, BorderLayout.CENTER);
        bottomPanel.add(addButton, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel label1 = new JLabel("Từ khóa 1");
        label1.setFont(new Font("Arial", Font.PLAIN, 13));
        keyword1Field = new JTextField();
        keyword1Field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel label2 = new JLabel("Từ khóa 2");
        label2.setFont(new Font("Arial", Font.PLAIN, 13));
        keyword2Field = new JTextField();
        keyword2Field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel label3 = new JLabel("Từ khóa 3");
        label3.setFont(new Font("Arial", Font.PLAIN, 13));
        keyword3Field = new JTextField();
        keyword3Field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        inputPanel.add(label1);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(keyword1Field);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(label2);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(keyword2Field);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(label3);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(keyword3Field);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        searchButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchButton.addActionListener(e -> performSearch());
        
        JButton priceButton = new JButton("Tìm giá sản phẩm");
        priceButton.setFont(new Font("Arial", Font.PLAIN, 13));
        priceButton.setBackground(new Color(200, 255, 200));
        priceButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        priceButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        priceButton.addActionListener(e -> performPriceSearch());
        
        inputPanel.add(searchButton);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(priceButton);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JLabel label = new JLabel("Kết quả");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panel.add(label, BorderLayout.NORTH);
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        resultArea.setForeground(new Color(200, 50, 50));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadWebsites() {
        List<String> websites = engine.getWebsites();
        StringBuilder sb = new StringBuilder();
        for (String website : websites) {
            sb.append(website).append("\n");
        }
        sb.append(".....");
        websiteList.setText(sb.toString());
    }
    
    private void performSearch() {
        String kw1 = keyword1Field.getText().trim();
        String kw2 = keyword2Field.getText().trim();
        String kw3 = keyword3Field.getText().trim();
        
        if (kw1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất từ khóa 1!");
            return;
        }
        
        List<Article> results = engine.search(kw1, kw2, kw3);
        
        StringBuilder sb = new StringBuilder();
        if (results.isEmpty()) {
            sb.append("Không tìm thấy kết quả nào.");
        } else {
            for (Article article : results) {
                sb.append("... ").append(article.getTitle()).append(" .....\n\n");
                sb.append("... ").append(article.getSource())
                  .append(" - ").append(article.getDate()).append(" .....\n\n");
            }
        }
        
        resultArea.setText(sb.toString());
    }
    
    private void performPriceSearch() {
        String productName = JOptionPane.showInputDialog(this, "Nhập tên sản phẩm:");
        
        if (productName != null && !productName.trim().isEmpty()) {
            List<String> prices = engine.searchProductPrices(productName.trim());
            
            StringBuilder sb = new StringBuilder();
            sb.append("Kết quả tìm giá: ").append(productName).append("\n\n");
            for (String price : prices) {
                sb.append("• ").append(price).append("\n\n");
            }
            
            resultArea.setText(sb.toString());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SearchGUI gui = new SearchGUI();
            gui.setVisible(true);
        });
    }
}
