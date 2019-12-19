package testcoherencerest;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="person")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Person implements Serializable {

    public Person() {}

    public Person(String name, int age)
        {
        m_name = name;
        m_age  = age;
        }

    public String getName() { return m_name; }

    public void setName(String name) { m_name = name; }

    public int getAge() { return m_age; }

    public void setAge(int age) {  m_age = age; }

    protected String m_name;
    protected int    m_age;
}