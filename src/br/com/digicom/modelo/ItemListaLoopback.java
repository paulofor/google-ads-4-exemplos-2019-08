package br.com.digicom.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public interface ItemListaLoopback {

	public JSONObject toJson() throws JSONException;
}
