package ch04

import collection.JavaConverters._

import java.io.File
import org.apache.mahout.cf.taste.example.grouplens.GroupLensDataModel
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator
import org.apache.mahout.cf.taste.eval.RecommenderBuilder
import org.apache.mahout.cf.taste.impl.model.file._
import org.apache.mahout.cf.taste.impl.neighborhood._
import org.apache.mahout.cf.taste.impl.recommender._
import org.apache.mahout.cf.taste.impl.recommender.slopeone._
import org.apache.mahout.cf.taste.impl.similarity._
import org.apache.mahout.cf.taste.model._
import org.apache.mahout.cf.taste.neighborhood._
import org.apache.mahout.cf.taste.recommender._
import org.apache.mahout.cf.taste.similarity._
import org.apache.mahout.common.RandomUtils

/** 4.2.4 Explorling user neighborhoods */
object Neighborhood100 extends App {

  lazy val userBased = new RecommenderBuilder {
    def buildRecommender(model: DataModel) = {
      val similarity = new PearsonCorrelationSimilarity(model)
      val neighborhood = new NearestNUserNeighborhood(100, similarity, model)
      new GenericUserBasedRecommender(model, neighborhood, similarity)
    }
  } 

  RandomUtils.useTestSeed()
  val model = new GroupLensDataModel(new File("data/ml-10M100K/ratings.dat"))
  val evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator
  //val evaluator = new RMSRecommenderEvaluator

  val score = evaluator.evaluate(userBased, null, model, 0.95, 0.05)
  println(score)
}

