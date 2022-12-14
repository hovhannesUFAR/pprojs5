import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AdminManageKeywordsWindow extends JFrame implements ActionListener {
    private JPanel mainPanel = new JPanel();
    private JPanel creationPanel = new JPanel();
    private JPanel keywordNamePanel = new JPanel();

    private JLabel keywordNameLbl = new JLabel("Keyword Name");
    private JTextField keywordNameInput = new JTextField(20);

    private JPanel keywordWeightPanel = new JPanel();

    private ButtonGroup weightsGroup = new ButtonGroup();
    private JRadioButton[] radioButtons = new JRadioButton[]{new JRadioButton("Awful"), new JRadioButton("Bad"),
                                                new JRadioButton("Normal"), new JRadioButton("Good"), new JRadioButton("Excellent")};

    private JButton createKeywordBtn = new JButton("Create");


    private JPanel keywordsViewPanel = new JPanel();
    private JScrollPane keywordsScrollPane = null;
    private JTable keywordsTbl = null;
    public void addKeywordRow(Keyword keyword) {
        DefaultTableModel model = (DefaultTableModel) keywordsTbl.getModel();
        model.addRow(new Object[]{keyword.getId(), keyword.getName(), WeightDAO.getInstance().getWeightById(keyword.getWeightId()).getName()});
    }

    private void clearInputs() {
        keywordNameInput.setText("");
        weightsGroup.clearSelection();

    }
    private boolean isValidCreationAttempt(String keywordName, String keywordWeightName) {
        keywordName = keywordName.toLowerCase();
        Keyword realKeyword = KeywordDAO.getInstance().getKeywordByName(keywordName);
        return null == realKeyword
                && (keywordName.length() > 0 && keywordName.length() <= 45)
                && !(keywordWeightName.equals(""))
                && (null != WeightDAO.getInstance().getWeightByName(keywordWeightName));
    }
    private String getSelectedRadioButton() {
        for (JRadioButton x : radioButtons) {
            if (x.isSelected()) {
                return x.getText();
            }
        }
        return "";
    }
    private void doCreate(String keywordName, int keywordWeightId) {
        keywordName = keywordName.toLowerCase();
        KeywordDAO.getInstance().saveKeyword(new Keyword(keywordName, keywordWeightId));
        addKeywordRow(KeywordDAO.getInstance().getKeywordByName(keywordName));
    }

    public AdminManageKeywordsWindow() {

        setTitle("Admin Manage Keywords");
        setLayout(new FlowLayout());

        creationPanel.setLayout(new BoxLayout(creationPanel, BoxLayout.PAGE_AXIS));

        keywordNamePanel.add(keywordNameLbl);
        keywordNamePanel.add(keywordNameInput);
        creationPanel.add(keywordNamePanel);


        for (JRadioButton x : radioButtons) {
            keywordWeightPanel.add(x);
            weightsGroup.add(x);
        }

        creationPanel.add(keywordWeightPanel);

        creationPanel.add(createKeywordBtn);


        createKeywordBtn.addActionListener(this);

        mainPanel.add(creationPanel);

        Vector<String> columns = new Vector<String>();
        columns.add("id");
        columns.add("name");
        columns.add("weight");

        keywordsTbl = new JTable(KeywordDAO.getInstance().getAllKeywordsTableData(), columns);
        keywordsTbl.setEnabled(false);

        keywordsScrollPane = new JScrollPane(keywordsTbl);
        keywordsViewPanel.add(keywordsScrollPane);

        mainPanel.add(keywordsViewPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        add(mainPanel);


        setSize(600, 600);
        setVisible(false);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createKeywordBtn) {
            String selectedWeightName = getSelectedRadioButton();
            if (isValidCreationAttempt(keywordNameInput.getText(), selectedWeightName)) {
                doCreate(keywordNameInput.getText(), WeightDAO.getInstance().getWeightByName(selectedWeightName).getId());
                clearInputs();
            }
        }
    }
}
