package entity;

public class Return {
	
	private Integer _id = null;
	private String _error = null;
	private Object _return = null;

	public Return(Integer id, Object retval, String error) {
		this._id = id;
		this._return = retval;
		this._error = error == null || error.trim() == "" ? null : error.trim();
	}
	
	public final Integer getId() {
		return _id;
	}

	public final void setId(Integer id) {
		this._id = id;
	}

	public final Object getReturn() {
		return this._return;
	}
	
	public final void setReturn(Object value) {
		this._return = value;
	}

	public final String getError() {
		return _error;
	}

	public final void setError(String error) {
		this._error = error;
	}
}
