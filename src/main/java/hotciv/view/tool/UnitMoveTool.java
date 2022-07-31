package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.CivDrawing;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import hotciv.view.figure.UnitFigure;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

/** Template for the EndOfTurn Tool exercise (FRS 36.42)...
 *
 * @author Henrik Bærbak Christensen, Aarhus University
 */
public class UnitMoveTool extends SelectionTool {
    private final DrawingEditor editor;
    private final Game game;
    private Position from, to;
    private Unit movedUnit;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        super(editor);
        this.editor = editor;
        this.game = game;

    }
   
    @Override
    public void mouseDown(MouseEvent e, int x, int y){
        super.mouseDown(e, x, y);
        from = GfxConstants.getPositionFromXY(x, y);
        movedUnit = game.getUnitAt(from);
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        super.mouseDrag(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y){
        super.mouseUp(e, x, y);
        to = GfxConstants.getPositionFromXY(x, y);
        boolean playerInTurn = game.getPlayerInTurn().equals(movedUnit.getOwner());
        boolean notSameTile = to != from;
        boolean legalDistance = Math.abs(from.getRow() - to.getRow()) <= 1 && Math.abs(from.getColumn() - to.getColumn()) <= 1;
        if(notSameTile && legalDistance && from != null && playerInTurn) {
            game.moveUnit(from, to);
        }
    }
}
