package br.com.erudio.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

/*
   (muda a ordem da exibição) @JsonPropertyOrder({"id","address","first_name","firstName","gender"})
*/

public class PersonVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	/*
	  ( muda o nome no json ) @JsonProperty("first_name")
	 */

	private String firstName;
	private String lastName;
	private String address;
	/*
	  (ignora atributos no json) @JsonIgnore
	 */
	
	private String gender;

	public PersonVO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, gender, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonVO other = (PersonVO) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName);
	}
}
