package br.com.jmoicano.android.bakingapp.util;

public interface ListAdapterViewModel<T> {
    int numItens();
    T getPosition(int i);
}
