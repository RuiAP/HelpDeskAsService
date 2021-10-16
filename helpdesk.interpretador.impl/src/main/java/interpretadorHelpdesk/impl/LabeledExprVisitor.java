package interpretadorHelpdesk.impl;// Generated from C:/Users/Admin/Documents/BitBucket/LAPR4/lei20_21_s4_nb_g01/helpdesk.interpretador.impl\LabeledExpr.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LabeledExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LabeledExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LabeledExprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(LabeledExprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link LabeledExprParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(LabeledExprParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link LabeledExprParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(LabeledExprParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link LabeledExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(LabeledExprParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variableAssignmentExpression}
	 * labeled alternative in {@link LabeledExprParser#variableAssignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignmentExpression(LabeledExprParser.VariableAssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code getSubStringExpression}
	 * labeled alternative in {@link LabeledExprParser#getSubStringStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetSubStringExpression(LabeledExprParser.GetSubStringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printExpression}
	 * labeled alternative in {@link LabeledExprParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpression(LabeledExprParser.PrintExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileExpression}
	 * labeled alternative in {@link LabeledExprParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExpression(LabeledExprParser.WhileExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifElseExpression}
	 * labeled alternative in {@link LabeledExprParser#ifElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseExpression(LabeledExprParser.IfElseExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnExpression}
	 * labeled alternative in {@link LabeledExprParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnExpression(LabeledExprParser.ReturnExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code allRecordsExpression}
	 * labeled alternative in {@link LabeledExprParser#allRecordsStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllRecordsExpression(LabeledExprParser.AllRecordsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code findRecordExpression}
	 * labeled alternative in {@link LabeledExprParser#findRecordStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFindRecordExpression(LabeledExprParser.FindRecordExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code findFirstRecordExpression}
	 * labeled alternative in {@link LabeledExprParser#findFirstRecordStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFindFirstRecordExpression(LabeledExprParser.FindFirstRecordExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sendEmailExpression}
	 * labeled alternative in {@link LabeledExprParser#sendEmailStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendEmailExpression(LabeledExprParser.SendEmailExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sleepExpression}
	 * labeled alternative in {@link LabeledExprParser#sleepStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSleepExpression(LabeledExprParser.SleepExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code greaterthanExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterthanExpression(LabeledExprParser.GreaterthanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpression(LabeledExprParser.IdentifierExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code modExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModExpression(LabeledExprParser.ModExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(LabeledExprParser.NotExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memoryAccessObjectXMLExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemoryAccessObjectXMLExpression(LabeledExprParser.MemoryAccessObjectXMLExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code divExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivExpression(LabeledExprParser.DivExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolLitExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLitExpression(LabeledExprParser.BoolLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code concatExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatExpression(LabeledExprParser.ConcatExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code objectXMLExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectXMLExpression(LabeledExprParser.ObjectXMLExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenthesesExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesesExpression(LabeledExprParser.ParenthesesExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(LabeledExprParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(LabeledExprParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpression(LabeledExprParser.StringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filledExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilledExpression(LabeledExprParser.FilledExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memoryAccessColumnCSVExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemoryAccessColumnCSVExpression(LabeledExprParser.MemoryAccessColumnCSVExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpression(LabeledExprParser.AddExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lessThanExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanExpression(LabeledExprParser.LessThanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(LabeledExprParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code characterExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacterExpression(LabeledExprParser.CharacterExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integerLitExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLitExpression(LabeledExprParser.IntegerLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code columnCSVExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnCSVExpression(LabeledExprParser.ColumnCSVExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubExpression(LabeledExprParser.SubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpression(LabeledExprParser.MulExpressionContext ctx);
}