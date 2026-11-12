class Student {
    //student attributes
    private String id;
    private String name;
    private String course;
    private int age;

    public Student ( String id, String name, String course, int age) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.age = age;
    }
//getters
    public String getId() {
        return id;
    }
     public String getName() {
         return name;
    }
    public String getCourse() {
        return course;
    }
    public int getAge() {
        return age;
    }
}

