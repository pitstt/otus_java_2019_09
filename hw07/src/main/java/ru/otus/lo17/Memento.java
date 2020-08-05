package ru.otus.lo17;

public class Memento {
  private final State state;

  Memento(State state) throws Exception {
    this.state = new State(state);
  }

  State getState() {
    return state;
  }
}
