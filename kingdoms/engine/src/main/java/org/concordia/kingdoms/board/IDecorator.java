package org.concordia.kingdoms.board;

import org.concordia.kingdoms.domain.Component;

public interface IDecorator extends Component {

	IDecorator setComponent(Component component);
}
