package net.physalis.mahouttest

import collection.JavaConverters._

import java.io.File
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

/** 2.3.2 Running RecommenderEvaluator */
object EvaluatorIntro extends App {

  lazy val userBased = new RecommenderBuilder {
    def buildRecommender(model: DataModel) = {
      val similarity = new PearsonCorrelationSimilarity(model)
      val neighborhood = new NearestNUserNeighborhood(2, similarity, model)
      new GenericUserBasedRecommender(model, neighborhood, similarity)
    }
  } 

  lazy val slopeOne = new RecommenderBuilder {
    def buildRecommender(model: DataModel) = new SlopeOneRecommender(model)
  }

  RandomUtils.useTestSeed()
  //val model = new FileDataModel(new File("../MIA/src/main/java/mia/recommender/ch02/intro.csv"))
  val model = new FileDataModel(new File("data/ml-100k/ua.base"))
  val evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator
  //val evaluator = new RMSRecommenderEvaluator

  val score = evaluator.evaluate(slopeOne, null, model, 0.7, 1.0)
  println(score)
}

