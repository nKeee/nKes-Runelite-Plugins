package nKesAdvancednotifications.ui.condition;

import nKesAdvancednotifications.DraggableContainer;
import nKesAdvancednotifications.condition.Condition;
import nKesAdvancednotifications.condition.EmptyCondition;
import nKesAdvancednotifications.condition.InventoryChecker;
import nKesAdvancednotifications.ui.DropSpaceSystem;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import java.awt.*;

public class EmptyConditionPanel extends ConditionPanel<EmptyCondition>
{
	private final SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
	private final JSpinner countSpinner = new JSpinner(spinnerModel);

	public EmptyConditionPanel(
		EmptyCondition condition,
		DropSpaceSystem<Condition> system,
		DraggableContainer<Condition> container
	)
	{
		super(condition, system, container);
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARKER_GRAY_COLOR);

		DefaultTypePanel typePanel = new DefaultTypePanel(this, "Empty Space Condition");
		typePanel.addDefaultVisualListener();

		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		contentPanel.setOpaque(false);

		JPanel paramsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paramsPanel.setOpaque(false);

		JLabel countLabel = new JLabel("Count ");
		countLabel.setForeground(Color.WHITE);

		JComboBox<InventoryChecker> comparatorBox = new JComboBox<>(InventoryChecker.CHECKERS);
		comparatorBox.setSelectedItem(condition.getChecker().object);
		comparatorBox.setPreferredSize(new Dimension(50, 20));
		comparatorBox.setMaximumRowCount(9);
		comparatorBox.addItemListener(e -> {
			condition.getChecker().object = (InventoryChecker)comparatorBox.getSelectedItem();
			condition.getPlugin().updateConfig();
			countSpinner.setVisible(condition.getChecker().object.takesParam());
		});

		countSpinner.setValue(condition.getCheckerParam());
		countSpinner.setPreferredSize(new Dimension(64, 20));
		countSpinner.setVisible(condition.getChecker().object.takesParam());
		countSpinner.addChangeListener(e -> {
			condition.setCheckerParam((Integer)countSpinner.getValue());
			condition.getPlugin().updateConfig();
		});

		paramsPanel.add(countLabel);
		paramsPanel.add(comparatorBox);
		paramsPanel.add(countSpinner);

		contentPanel.add(paramsPanel, BorderLayout.SOUTH);

		add(typePanel, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
	}
}
