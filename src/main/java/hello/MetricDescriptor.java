package hello;

public class MetricDescriptor {
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	private String resourceType;
	private String kind;
	private String valueType;

	public MetricDescriptor(String type,String resourceType,String kind,String valueType) {
		this.type = type;
		this.resourceType = resourceType;
		this.kind =  kind;
		this.valueType = valueType;
	}
	
	public static class MetricDescriptorBuilder {
		private String type;
		private String resourceType;
		private String kind;
		private String valueType;
		
		
		public MetricDescriptorBuilder(){
			
		}
		
		MetricDescriptorBuilder fromType(String type){
			this.type = type;
			return this;
		}
		
		MetricDescriptorBuilder fromResourceType(String resourceType){
			this.resourceType = resourceType;
			return this;
		}
		
		MetricDescriptorBuilder fromKind(String kind){
			this.kind = kind;
			return this;
		}
		
		MetricDescriptorBuilder fromValueType(String valueType){
			this.valueType = valueType;
			return this;
		}
		
		MetricDescriptor createDescriptor(){
			return new MetricDescriptor(type, resourceType, kind, valueType);
		}
	}
	
}
