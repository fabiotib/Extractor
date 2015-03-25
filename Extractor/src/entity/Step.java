package entity;

import enu.ENUM_ACTION;
import enu.ENUM_COMMAND;

public class Step {

	private Integer _id = null;
	private String _parameter = null;
	private String _parameter2 = null;
	private ENUM_COMMAND _enuCommand = null;
	private Object _result = null;
	private Object _originalResult = null;
	private ENUM_ACTION _enuNextAction = null;
	private String _error = null;

	public Step(ENUM_COMMAND enuCommand, String parameter){
		this(enuCommand, parameter, null);
	}

	public Step(ENUM_COMMAND enuCommand){
		this(enuCommand, null, null);
	}

	public Step(ENUM_COMMAND enuCommand, String parameter, String parameter2){
		this(null, enuCommand, parameter, parameter2);
	}

	public Step(Integer id, ENUM_COMMAND enuCommand, String parameter, String parameter2){
		this(id, enuCommand, parameter, parameter2, null);
	}

	public Step(Integer id, ENUM_COMMAND enuCommand, String parameter, String parameter2, String error){
		this._id = id;
		this._enuCommand = enuCommand;
		this._parameter = parameter == null || parameter.trim() == "" ? null : parameter.trim();
		this._parameter2 = parameter2 == null || parameter2.trim() == "" ? null : parameter2.trim();
		this._error = error == null || error.trim() == "" ? null : error.trim();
	}
	
	public final Integer getId() {
		return _id;
	}

	public final void setId(Integer id) {
		this._id = id;
	}

	public final Object getResult() {
		return _result;
	}

	public final Object getOriginalResult() {
		return _originalResult;
	}

	public final void setResult(Object result) {
		if (this._originalResult == null && this._result != null)
			this._originalResult = this._result;
		
		this._result = result;
	}

	public final String getParameter() {
		return _parameter;
	}
	
	public final void setParameter(String parameter) {
		this._parameter = parameter;
	}

	public final String getparameter2() {
		return _parameter2;
	}

	public final void setParameter2(String parameter2) {
		this._parameter2 = parameter2;
	}

	public final ENUM_COMMAND getEnuCommand() {
		return _enuCommand;
	}
	public final void setEnuCommand(ENUM_COMMAND enuCommand) {
		this._enuCommand = enuCommand;
	}
	
	public final ENUM_ACTION getNextAction() {
		return _enuNextAction;
	}
	public final void setNextAction(ENUM_ACTION enuNextAction) {
		this._enuNextAction = enuNextAction;
	}
	
	public final String getError() {
		return _error;
	}

	public final void setError(String error) {
		this._error = error;
		this._enuNextAction = ENUM_ACTION.ERROR;
	}

}
