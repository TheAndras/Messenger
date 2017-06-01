package hu.mik.MZ8AEX.Messenger;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class RegisterUI extends CssLayout {
	protected TextField txtUsername;
	protected TextField txtFirstName;
	protected TextField txtLastName;
	protected HorizontalLayout dateOfBirthWrapper;
	protected TextField txtYear;
	protected TextField txtMonth;
	protected TextField txtDay;
	protected CssLayout phoneNumberWrapper;
	protected TextField countryCode;
	protected TextField txtPhoneNumber;
	protected TextField txtEmail;
	protected Button btnRegister;
	protected Button btnCancel;

	public RegisterUI() {
		Design.read(this);
	}
}