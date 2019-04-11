package did;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import did.parser.Displayer;
import did.parser.Parser;
import did.parser.ParserException;
import did.parser.Rule;
import did.parser.Rule_did;
import did.parser.Rule_did_url;
import did.parser.Rule_fragment;
import did.parser.Rule_method;
import did.parser.Rule_method_specific_id;
import did.parser.Rule_param_name;
import did.parser.Rule_param_value;
import did.parser.Rule_path_abempty;
import did.parser.Rule_query;
import did.parser.Terminal_NumericValue;
import did.parser.Terminal_StringValue;

public class DID {

	public static final String URI_SCHEME = "did";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private String didUrl;
	private String did;
	private String method;
	private String methodSpecificId;
	private Map<String, String> parameters = new HashMap<String, String> ();
	private String path;
	private String query;
	private String fragment;

	private DID() {

	}

	private DID(String didUrl) throws IllegalArgumentException, ParserException {

		this.didUrl = didUrl;

		this.parse();
	}

	private void parse() throws IllegalArgumentException, ParserException {

		Rule_did_url rule = (Rule_did_url) Parser.parse("did-url", this.didUrl);
		rule.accept(new DIDVisitor());
	}

	/*
	 * Factory methods
	 */

	public static DID fromString(String string) throws IllegalArgumentException, ParserException {

		return new DID(string);
	}

	public static DID fromUri(URI uri) throws IllegalArgumentException, ParserException {

		return fromString(uri.toString());
	}

	/*
	 * Serialization
	 */

	public static DID fromJson(String json) throws JsonParseException, JsonMappingException, IOException {

		return objectMapper.readValue(json, DID.class);
	}

	public String toJson() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this);
	}

	/*
	 * Helper classes
	 */

	private class DIDVisitor extends Displayer {

		public Object visit(Rule_did rule) {

			DID.this.did = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_method rule) {

			DID.this.method = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_method_specific_id rule) {

			DID.this.methodSpecificId = rule.spelling;
			return visitRules(rule.rules);
		}

		private String param_name = null;
		
		public Object visit(Rule_param_name rule) {

			param_name = rule.spelling;
			DID.this.parameters.put(rule.spelling, null);
			return visitRules(rule.rules);
		}

		public Object visit(Rule_param_value rule) {

			DID.this.parameters.put(param_name, rule.spelling);
			return visitRules(rule.rules);
		}

		public Object visit(Rule_path_abempty rule) {

			DID.this.path = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_query rule) {

			DID.this.query = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_fragment rule) {

			DID.this.fragment = rule.spelling;
			return visitRules(rule.rules);
		}

		@Override
		public Object visit(Terminal_StringValue value) {

			return null;
		}

		@Override
		public Object visit(Terminal_NumericValue value) {
			return null;
		}

		private Object visitRules(ArrayList<Rule> rules) {

			for (Rule rule : rules) rule.accept(this);
			return null;
		}
	}

	/*
	 * Getters
	 */

	@JsonGetter
	public final String getDidUrl() {

		return this.didUrl;
	}

	@JsonSetter
	public final void setDidUrl(String didUrl) {

		this.didUrl = didUrl;
	}

	@JsonGetter
	public final String getDid() {

		return this.did;
	}

	@JsonSetter
	public final void setDid(String did) {

		this.did = did;
	}

	@JsonGetter
	public final String getMethod() {

		return this.method;
	}

	@JsonSetter
	public final void setMethod(String method) {

		this.method = method;
	}

	@JsonGetter
	public final String getMethodSpecificId() {

		return this.methodSpecificId;
	}

	@JsonSetter
	public final void setMethodSpecificId(String methodSpecificId) {

		this.methodSpecificId = methodSpecificId;
	}

	@JsonGetter
	public final Map<String, String> getParameters() {

		return this.parameters;
	}

	@JsonSetter
	public final void setService(Map<String, String> parameters) {

		this.parameters = parameters;
	}

	@JsonGetter
	public final String getPath() {

		return this.path;
	}

	@JsonSetter
	public final void setPath(String path) {

		this.path = path;
	}

	@JsonGetter
	public final String getQuery() {

		return this.query;
	}

	@JsonSetter
	public final void setQuery(String query) {

		this.query = query;
	}

	@JsonGetter
	public final String getFragment() {

		return this.fragment;
	}

	@JsonSetter
	public final void setFragment(String fragment) {

		this.fragment = fragment;
	}

	/*
	 * Object methods
	 */

	@Override
	public int hashCode() {

		return this.didUrl.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return this.didUrl.equals(obj);
	}

	@Override
	public String toString() {

		return this.didUrl.toString();
	}
}
