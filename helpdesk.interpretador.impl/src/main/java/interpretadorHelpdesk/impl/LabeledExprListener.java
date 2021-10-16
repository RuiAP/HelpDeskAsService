package interpretadorHelpdesk.impl;// Generated from C:/Users/Admin/Documents/BitBucket/LAPR4/lei20_21_s4_nb_g01/helpdesk.interpretador.impl\LabeledExpr.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LabeledExprParser}.
 */
public interface LabeledExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(LabeledExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(LabeledExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(LabeledExprParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(LabeledExprParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(LabeledExprParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(LabeledExprParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link LabeledExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(LabeledExprParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LabeledExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(LabeledExprParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableAssignmentExpression}
	 * labeled alternative in {@link LabeledExprParser#variableAssignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignmentExpression(LabeledExprParser.VariableAssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableAssignmentExpression}
	 * labeled alternative in {@link LabeledExprParser#variableAssignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignmentExpression(LabeledExprParser.VariableAssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code getSubStringExpression}
	 * labeled alternative in {@link LabeledExprParser#getSubStringStatement}.
	 * @param ctx the parse tree
	 */
	void enterGetSubStringExpression(LabeledExprParser.GetSubStringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code getSubStringExpression}
	 * labeled alternative in {@link LabeledExprParser#getSubStringStatement}.
	 * @param ctx the parse tree
	 */
	void exitGetSubStringExpression(LabeledExprParser.GetSubStringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printExpression}
	 * labeled alternative in {@link LabeledExprParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpression(LabeledExprParser.PrintExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpression}
	 * labeled alternative in {@link LabeledExprParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpression(LabeledExprParser.PrintExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileExpression}
	 * labeled alternative in {@link LabeledExprParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileExpression(LabeledExprParser.WhileExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileExpression}
	 * labeled alternative in {@link LabeledExprParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileExpression(LabeledExprParser.WhileExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifElseExpression}
	 * labeled alternative in {@link LabeledExprParser#ifElseStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfElseExpression(LabeledExprParser.IfElseExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifElseExpression}
	 * labeled alternative in {@link LabeledExprParser#ifElseStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfElseExpression(LabeledExprParser.IfElseExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnExpression}
	 * labeled alternative in {@link LabeledExprParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnExpression(LabeledExprParser.ReturnExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnExpression}
	 * labeled alternative in {@link LabeledExprParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnExpression(LabeledExprParser.ReturnExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code allRecordsExpression}
	 * labeled alternative in {@link LabeledExprParser#allRecordsStatement}.
	 * @param ctx the parse tree
	 */
	void enterAllRecordsExpression(LabeledExprParser.AllRecordsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code allRecordsExpression}
	 * labeled alternative in {@link LabeledExprParser#allRecordsStatement}.
	 * @param ctx the parse tree
	 */
	void exitAllRecordsExpression(LabeledExprParser.AllRecordsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code findRecordExpression}
	 * labeled alternative in {@link LabeledExprParser#findRecordStatement}.
	 * @param ctx the parse tree
	 */
	void enterFindRecordExpression(LabeledExprParser.FindRecordExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code findRecordExpression}
	 * labeled alternative in {@link LabeledExprParser#findRecordStatement}.
	 * @param ctx the parse tree
	 */
	void exitFindRecordExpression(LabeledExprParser.FindRecordExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code findFirstRecordExpression}
	 * labeled alternative in {@link LabeledExprParser#findFirstRecordStatement}.
	 * @param ctx the parse tree
	 */
	void enterFindFirstRecordExpression(LabeledExprParser.FindFirstRecordExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code findFirstRecordExpression}
	 * labeled alternative in {@link LabeledExprParser#findFirstRecordStatement}.
	 * @param ctx the parse tree
	 */
	void exitFindFirstRecordExpression(LabeledExprParser.FindFirstRecordExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sendEmailExpression}
	 * labeled alternative in {@link LabeledExprParser#sendEmailStatement}.
	 * @param ctx the parse tree
	 */
	void enterSendEmailExpression(LabeledExprParser.SendEmailExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sendEmailExpression}
	 * labeled alternative in {@link LabeledExprParser#sendEmailStatement}.
	 * @param ctx the parse tree
	 */
	void exitSendEmailExpression(LabeledExprParser.SendEmailExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sleepExpression}
	 * labeled alternative in {@link LabeledExprParser#sleepStatement}.
	 * @param ctx the parse tree
	 */
	void enterSleepExpression(LabeledExprParser.SleepExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sleepExpression}
	 * labeled alternative in {@link LabeledExprParser#sleepStatement}.
	 * @param ctx the parse tree
	 */
	void exitSleepExpression(LabeledExprParser.SleepExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code greaterthanExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGreaterthanExpression(LabeledExprParser.GreaterthanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code greaterthanExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGreaterthanExpression(LabeledExprParser.GreaterthanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(LabeledExprParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(LabeledExprParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code modExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterModExpression(LabeledExprParser.ModExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code modExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitModExpression(LabeledExprParser.ModExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(LabeledExprParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(LabeledExprParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memoryAccessObjectXMLExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemoryAccessObjectXMLExpression(LabeledExprParser.MemoryAccessObjectXMLExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memoryAccessObjectXMLExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemoryAccessObjectXMLExpression(LabeledExprParser.MemoryAccessObjectXMLExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code divExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDivExpression(LabeledExprParser.DivExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code divExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDivExpression(LabeledExprParser.DivExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolLitExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBoolLitExpression(LabeledExprParser.BoolLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolLitExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBoolLitExpression(LabeledExprParser.BoolLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code concatExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConcatExpression(LabeledExprParser.ConcatExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code concatExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConcatExpression(LabeledExprParser.ConcatExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objectXMLExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectXMLExpression(LabeledExprParser.ObjectXMLExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objectXMLExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectXMLExpression(LabeledExprParser.ObjectXMLExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthesesExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesesExpression(LabeledExprParser.ParenthesesExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthesesExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesesExpression(LabeledExprParser.ParenthesesExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(LabeledExprParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(LabeledExprParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(LabeledExprParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(LabeledExprParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringExpression(LabeledExprParser.StringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringExpression(LabeledExprParser.StringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filledExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFilledExpression(LabeledExprParser.FilledExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filledExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFilledExpression(LabeledExprParser.FilledExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memoryAccessColumnCSVExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemoryAccessColumnCSVExpression(LabeledExprParser.MemoryAccessColumnCSVExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memoryAccessColumnCSVExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemoryAccessColumnCSVExpression(LabeledExprParser.MemoryAccessColumnCSVExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(LabeledExprParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(LabeledExprParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lessThanExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLessThanExpression(LabeledExprParser.LessThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lessThanExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLessThanExpression(LabeledExprParser.LessThanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(LabeledExprParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(LabeledExprParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code characterExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCharacterExpression(LabeledExprParser.CharacterExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code characterExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCharacterExpression(LabeledExprParser.CharacterExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integerLitExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLitExpression(LabeledExprParser.IntegerLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integerLitExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLitExpression(LabeledExprParser.IntegerLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code columnCSVExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterColumnCSVExpression(LabeledExprParser.ColumnCSVExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code columnCSVExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitColumnCSVExpression(LabeledExprParser.ColumnCSVExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpression(LabeledExprParser.SubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpression(LabeledExprParser.SubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulExpression(LabeledExprParser.MulExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link LabeledExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulExpression(LabeledExprParser.MulExpressionContext ctx);
}