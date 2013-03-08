package org.concordia.kingdoms.adapter;

public interface IAdapter<K, V> {

	public V convertTo(K k);

	public K convertFrom(V v);
}
