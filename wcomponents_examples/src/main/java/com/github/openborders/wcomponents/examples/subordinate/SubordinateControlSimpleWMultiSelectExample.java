package com.github.openborders.wcomponents.examples.subordinate;

import com.github.openborders.wcomponents.Margin;
import com.github.openborders.wcomponents.WContainer;
import com.github.openborders.wcomponents.WField;
import com.github.openborders.wcomponents.WFieldLayout;
import com.github.openborders.wcomponents.WMultiSelect;
import com.github.openborders.wcomponents.WTextField;
import com.github.openborders.wcomponents.subordinate.Equal;
import com.github.openborders.wcomponents.subordinate.Hide;
import com.github.openborders.wcomponents.subordinate.Rule;
import com.github.openborders.wcomponents.subordinate.Show;
import com.github.openborders.wcomponents.subordinate.WSubordinateControl;

/**
 * This example demonstrates showing/hiding an extra field depending on
 * the selection in a {@link WMultiSelect}.
 *  
 * @author Martin Shevchenko
 * @since 1.0.0
 */
public class SubordinateControlSimpleWMultiSelectExample extends WContainer
{
    private static final String OPTION_A = "a";
    private static final String OPTION_B = "bc";
    private static final String OPTION_C = "c";
    
    /**
     * Creates a SubordinateControlSimpleWMultiSelectExample.
     */
    public SubordinateControlSimpleWMultiSelectExample()
    {
        
        WMultiSelect groupSelect = getGroupSelect();
        groupSelect.setOptions(new String[] {OPTION_A, OPTION_B, OPTION_C});
        
        WFieldLayout layout = new WFieldLayout();
        layout.setLabelWidth(25);
        layout.setMargin(new Margin(0, 0, 12, 0));
        add(layout);
        layout.addField("Select one or more options", groupSelect).getLabel().setHint("Option c requires further information");
        WField extraField = layout.addField("Extra information", new WTextField());
        WSubordinateControl control = new WSubordinateControl();
        add(control);
        
        Rule rule = new Rule();
        rule.setCondition(new Equal(groupSelect, OPTION_C));
        rule.addActionOnTrue(new Show(extraField));
        rule.addActionOnFalse(new Hide(extraField));
        control.addRule(rule);
    }
    
    /** @return the list box used in the example. */
    public WMultiSelect getGroupSelect()
    {
        return new WMultiSelect();
    }
}