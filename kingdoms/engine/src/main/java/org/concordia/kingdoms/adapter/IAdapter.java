package org.concordia.kingdoms.adapter;
/**
 * @author Team K
 * @since 1.1
 */
public interface IAdapter<K, V> {

	public V convertTo(K k);

	public K convertFrom(V v);
}
