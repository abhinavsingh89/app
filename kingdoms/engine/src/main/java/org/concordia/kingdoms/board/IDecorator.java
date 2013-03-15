package org.concordia.kingdoms.board;

import org.concordia.kingdoms.domain.Component;
/**
 * @author Team K
 * @since 1.1
 */
public interface IDecorator extends Component {

	IDecorator setComponent(Component component);
}
