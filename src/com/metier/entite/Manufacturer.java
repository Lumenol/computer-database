package com.metier.entite;

import java.util.Objects;

public class Manufacturer {

	public static class ManufacturerBuilder {

		private ManufacturerBuilder() {
		}

		private Long id;
		private String name;

		public Manufacturer build() {
			return new Manufacturer(id, name);
		}

		public ManufacturerBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ManufacturerBuilder id(long id) {
			this.id = id;
			return this;
		}

	}

	public static ManufacturerBuilder builder() {
		return new ManufacturerBuilder();
	}

	private final Long id;
	private final String name;

	private Manufacturer(Long id, String name) {
		Objects.requireNonNull(name);
		this.id = id;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manufacturer other = (Manufacturer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Manufacturer [id=" + id + ", name=" + name + "]";
	}

	public Long getId() {
		return id;
	}

}
