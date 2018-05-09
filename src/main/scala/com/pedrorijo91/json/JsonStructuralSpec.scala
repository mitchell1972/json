package com.pedrorijo91.json

import org.scalatest.matchers.{MatchResult, Matcher}
import play.api.libs.json._

import scala.reflect.ClassTag

trait JsonStructuralSpec{

  class JsValueTypeMatcher[T <: JsValue : ClassTag](name : String) extends Matcher[JsValue] {
    override def apply(left: JsValue): MatchResult = {
      MatchResult(implicitly[ClassTag[T]].runtimeClass.isAssignableFrom((left \ name).getClass),
        s"$name was not of type ${implicitly[ClassTag[T]].runtimeClass.getSimpleName}",
        s"$name was of type ${implicitly[ClassTag[T]].runtimeClass.getSimpleName}")
    }
  }

  class JsFieldExistsMatcher(name : String) extends Matcher[JsValue] {
    override def apply(left: JsValue): MatchResult = {
      MatchResult(!classOf[JsUndefined].isInstance(left \ name),
        s"Field $name was not defined in $left",
        s"Field $name was defined")
    }
  }

  class JsFieldDoesNotExistMatcher(name : String) extends Matcher[JsValue] {
    override def apply(left: JsValue): MatchResult = {
      MatchResult(classOf[JsUndefined].isInstance(left \ name),
        s"Field $name was defined",
        s"Field $name was not defined in $left"
        )
    }
  }

  class FieldTypeMatcher[T <: JsValue : ClassTag](fields : String*) extends Matcher[JsValue] {
    override def apply(left: JsValue): MatchResult = {
      (fields map { fieldName =>
        new JsFieldExistsMatcher(fieldName)
            .and(new JsValueTypeMatcher[T](fieldName))
      } reduce {(a,b) => a and b})
        .apply(left)
    }
  }

  class OptionalFieldTypeMatcher[T <: JsValue : ClassTag](fields : String*) extends Matcher[JsValue] {
    override def apply(left: JsValue): MatchResult = {
      (fields map { fieldName =>
        new JsFieldExistsMatcher(fieldName)
          .and(new JsValueTypeMatcher[T](fieldName))
          .or(new JsFieldDoesNotExistMatcher(fieldName))
      } reduce {(a,b) => a and b})
        .apply(left)
    }
  }

  def haveNumericJsonFields(fields : String*): FieldTypeMatcher[JsNumber] = {new FieldTypeMatcher[JsNumber](fields:_*)}
  def haveOptionalNumericJsonFields(fields : String*): OptionalFieldTypeMatcher[JsNumber] = {new OptionalFieldTypeMatcher[JsNumber](fields:_*)}
  def haveStringJsonFields(fields : String*): FieldTypeMatcher[JsString] = {new FieldTypeMatcher[JsString](fields:_*)}
  def haveBooleanJsonFields(fields : String*): FieldTypeMatcher[JsBoolean] = {new FieldTypeMatcher[JsBoolean](fields:_*)}
}