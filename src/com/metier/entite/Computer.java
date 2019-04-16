package com.metier.entite;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Computer {

	public static class ComputerBuilder {
		private Long id;
		private String name;
		private Compagny manufacturer;
		private LocalDate introduced;
		private LocalDate discontinued;

		public Computer build() {
			return new Computer(id, name, manufacturer, introduced, Optional.ofNullable(discontinued));
		}

		public ComputerBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder id(long id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder manufacturer(Compagny manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		public ComputerBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder discontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

	}

	private final Long id;
	private final String name;
	private final Compagny manufacturer;
	private final LocalDate introduced;
	private final Optional<LocalDate> discontinued;

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
		Computer other = (Computer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + "]";
	}

	public String getName() {
		return name;
	}

	public Compagny getManufacturer() {
		return manufacturer;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public Optional<LocalDate> getDiscontinued() {
		return discontinued;
	}

	public static ComputerBuilder builder() {
		return new ComputerBuilder();
	}

	private Computer(Long id, String name, Compagny manufacturer, LocalDate introduced,
			Optional<LocalDate> discontinued) {
		Objects.requireNonNull(manufacturer);

		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public Long getId() {
		return id;
	}

}
