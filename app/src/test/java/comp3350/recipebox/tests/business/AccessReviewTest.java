package comp3350.recipebox.tests.business;

import java.util.List;

import org.junit.Test;

import comp3350.recipebox.application.Services;
import comp3350.recipebox.business.AccessReview;
import comp3350.recipebox.objects.Recipe;
import comp3350.recipebox.objects.Review;
import comp3350.recipebox.objects.User;
import comp3350.recipebox.persistence.DataAccess;
import comp3350.recipebox.tests.persistence.DataAccessStub;

import junit.framework.Assert;

public class AccessReviewTest
{
	public AccessReview accessReview;

	public AccessReviewTest()
	{
		String testing = "testing";
		DataAccess dataAccess = new DataAccessStub(testing);
		Services.createDataAccess(dataAccess);
		accessReview = new AccessReview(dataAccess);
	}

	@Test
	public void testAccessor()
	{

		Recipe dummyRecipe = new Recipe(0,0);

		Assert.assertTrue(accessReview.addReview(dummyRecipe, 5, "   test content   ", new User(1, "test", "password")));

		List<Review> reviews = accessReview.getReviewsByRecipe(dummyRecipe);
		Review newReview = reviews.get(reviews.size() - 1);

		Assert.assertEquals(newReview.getContent(), "test content");
		Assert.assertEquals(newReview.getRating(), 5);

		accessReview.deleteReview(newReview.getReviewID());
		Assert.assertNull(accessReview.getReview(newReview.getReviewID()));
	}

	@Test
	public void testInVailField()
	{
		Recipe dummyRecipe;
		dummyRecipe = new Recipe(Integer.MAX_VALUE, 0);
		Assert.assertFalse(accessReview.addReview(dummyRecipe, 1, "test content", new User(1, "test", "password")));
		dummyRecipe = new Recipe(0, 0);
		Assert.assertFalse(
				accessReview.addReview(dummyRecipe, Integer.MAX_VALUE, "test content", new User(1, "test", "password")));
	}

}
