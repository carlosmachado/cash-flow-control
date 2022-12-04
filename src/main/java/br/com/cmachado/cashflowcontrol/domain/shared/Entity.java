package br.com.cmachado.cashflowcontrol.domain.shared;

public interface Entity<T> {
    boolean sameIdentityAs(T other);
}
