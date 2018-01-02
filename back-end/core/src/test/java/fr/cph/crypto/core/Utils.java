package fr.cph.crypto.core;

import fr.cph.crypto.core.entity.Currency;
import fr.cph.crypto.core.entity.Role;
import fr.cph.crypto.core.entity.User;
import java.util.ArrayList;

public class Utils {
	public static User getUser() {
		return new User(null, "email", "password", Role.USER, Currency.USD, 0.0, true, null, null, null,
				null, new ArrayList<>());
	}
}
