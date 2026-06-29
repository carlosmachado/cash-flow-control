package br.com.cmachado.cashflow.shared.ddd;

public interface Entity<T> {
    boolean sameIdentityAs(T other);
}
