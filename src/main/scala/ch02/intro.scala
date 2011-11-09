package net.physalis.mahouttest

import collection.JavaConverters._

import java.io.File
import org.apache.mahout.cf.taste.impl.model.file._
import org.apache.mahout.cf.taste.impl.neighborhood._
import org.apache.mahout.cf.taste.impl.recommender._
import org.apache.mahout.cf.taste.impl.similarity._
import org.apache.mahout.cf.taste.model._
import org.apache.mahout.cf.taste.neighborhood._
import org.apache.mahout.cf.taste.recommender._
import org.apache.mahout.cf.taste.similarity._

/** 2.2.2 Creating a recommender */
object RecommenderIntro extends App {

  val model = new FileDataModel(new File("../MIA/src/main/java/mia/recommender/ch02/intro.csv"))
  val similarity = new PearsonCorrelationSimilarity(model)
  val neighborhood = new NearestNUserNeighborhood(2, similarity, model)
  val recommender = new GenericUserBasedRecommender(model, neighborhood, similarity)
  val recommendations = recommender.recommend(1, 1)

  recommendations.asScala.foreach(println _)
}

