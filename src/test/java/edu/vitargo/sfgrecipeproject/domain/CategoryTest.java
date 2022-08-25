package edu.vitargo.sfgrecipeproject.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category();
    }

    @Test
    public void getId() {
        Long id = 4L;
        category.setId(id);

        Assert.assertEquals(id, category.getId());
    }
}