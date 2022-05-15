package Window.GameStates;

import Framework.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    public boolean isIn(MouseEvent e, MenuButton mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
