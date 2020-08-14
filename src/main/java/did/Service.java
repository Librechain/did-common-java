package did;

import did.jsonld.DIDKeywords;
import did.jsonld.JsonLDObject;
import did.jsonld.JsonLDUtils;

import javax.json.JsonObject;
import java.util.Collections;
import java.util.List;

public class Service extends JsonLDObject {

	private Service() {
		super();
	}

	public Service(JsonObject jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, Service> {

		private String serviceEndpoint;

		public Builder() {
			super(new Service());
		}

		public Service build() {

			super.build();

			// add JSON-LD properties
			if (this.serviceEndpoint != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_SERVICEENDPOINT, this.serviceEndpoint);

			return this.jsonLDObject;
		}

		public Builder serviceEndpoint(String serviceEndpoint) {
			this.serviceEndpoint = serviceEndpoint;
			return this;
		}
	}

	public static Builder builder() {

		return new Builder();
	}

	/*
	 * Getters
	 */

	public String getServiceEndpoint() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICEENDPOINT);
	}
}