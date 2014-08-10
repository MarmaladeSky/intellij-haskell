/*
 * Copyright 2014 Rik van der Kleij

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.powertuple.intellij.haskell.psi.impl

import javax.swing._

import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.{PsiElement, PsiReference}
import com.intellij.util.ArrayUtil
import com.powertuple.intellij.haskell.HaskellIcons
import com.powertuple.intellij.haskell.external.GhcModiManager
import com.powertuple.intellij.haskell.psi._
import org.jetbrains.annotations.Nullable

import scala.collection.JavaConversions._

object HaskellPsiImplUtil {

  def getName(haskellVar: HaskellVar): String = {
    findFirstVarIdTokenChildPsiElementName(haskellVar)
  }

  def getId(haskellQvar: HaskellQvar): String = {
    val haskellVars = PsiTreeUtil.findChildrenOfType(haskellQvar, classOf[HaskellVar])
    haskellVars.map(_.getName).mkString(".")
  }

  def setName(haskellVar: HaskellVar, newName: String): PsiElement = {
    val keyNode = Option(haskellVar.getNode.findChildByType(HaskellTypes.HS_VAR_ID))
    keyNode match {
      case None => haskellVar
      case Some(kn) =>
        val newHaskellVar = HaskellElementFactory.createVar(haskellVar.getProject, newName)
        val newKeyNode = newHaskellVar.getFirstChild.getNode
        haskellVar.getNode.replaceChild(kn, newKeyNode)
        haskellVar
    }
  }

  def getNameIdentifier(haskellVar: HaskellVar): PsiElement = {
    findFirstVarIdTokenChildPsiElement(haskellVar)
  }

  def getReference(haskellVar: HaskellVar): PsiReference = {
    ArrayUtil.getFirstElement(ReferenceProvidersRegistry.getReferencesFromProviders(haskellVar))
  }


  def getName(haskellCon: HaskellCon): String = {
    findFirstConIdTokenChildPsiElementName(haskellCon)
  }

  def getId(haskellQcon: HaskellQcon): String = {
    val haskellCons = PsiTreeUtil.findChildrenOfType(haskellQcon, classOf[HaskellCon])
    haskellCons.map(_.getName).mkString(".")
  }

  def setName(haskellCon: HaskellCon, newName: String): PsiElement = {
    val keyNode = Option(haskellCon.getNode.findChildByType(HaskellTypes.HS_CON_ID))
    keyNode match {
      case None => haskellCon
      case Some(kn) =>
        val newHaskellCon = HaskellElementFactory.createCon(haskellCon.getProject, newName)
        val newKeyNode = newHaskellCon.getFirstChild.getNode
        haskellCon.getNode.replaceChild(kn, newKeyNode)
        haskellCon
    }
  }

  def getNameIdentifier(haskellCon: HaskellCon): PsiElement = {
    findFirstConIdTokenChildPsiElement(haskellCon)
  }

  def getPresentation(namedElement: HaskellNamedElement): ItemPresentation = {
    new ItemPresentation {
      @Nullable
      def getPresentableText: String = {
        val typeSignature: Option[String] = GhcModiManager.findTypeSignature(namedElement)
        typeSignature.getOrElse(namedElement.getName)
      }

      @Nullable
      def getLocationString: String = {
        namedElement.getContainingFile.getName
      }

      @Nullable
      def getIcon(unused: Boolean): Icon = {
        HaskellIcons.HASKELL_SMALL_LOGO
      }
    }
  }


  /**
   * Only returns first var. Could be a number of vars with same type signature.
   * TODO: fix this. Also #getIdentifier
   */
  def getIdentifierElement(TypeSignature: HaskellTypeSignature): HaskellNamedElement = {
    getFirstHaskellVar(TypeSignature)
  }

  def getIdentifier(TypeSignature: HaskellTypeSignature): String = {
    getFirstHaskellVarName(TypeSignature)
  }


  def getIdentifierElement(DataDeclaration: HaskellDataDeclaration): HaskellNamedElement = {
    val simpleType = PsiTreeUtil.findChildOfType(DataDeclaration, classOf[HaskellSimpletype])
    simpleType.getCon
  }

  def getIdentifier(DataDeclaration: HaskellDataDeclaration): String = {
    val haskellCon = getIdentifierElement(DataDeclaration)
    if (haskellCon != null) haskellCon.getName else null
  }


  def getIdentifierElement(typeDeclaration: HaskellTypeDeclaration): HaskellNamedElement = {
    PsiTreeUtil.findChildOfType(typeDeclaration, classOf[HaskellCon])
  }

  def getIdentifier(typeDeclaration: HaskellTypeDeclaration): String = {
    getIdentifierElement(typeDeclaration).getName
  }


  def getModuleName(importDeclaration: HaskellImportDeclaration): String = {
    val haskellQcon: HaskellQcon = PsiTreeUtil.findChildOfType(importDeclaration, classOf[HaskellQcon])
    if (haskellQcon != null) haskellQcon.getId else null
  }


  def getId(haskellSimpleType: HaskellSimpletype): String = {
    haskellSimpleType.getCon.getName
  }


  private def findFirstVarIdTokenChildPsiElement(compositeElement: HaskellCompositeElement): PsiElement = {
    val keyNode: ASTNode = compositeElement.getNode.findChildByType(HaskellTypes.HS_VAR_ID)
    if (keyNode != null) keyNode.getPsi else null
  }

  private def findFirstVarIdTokenChildPsiElementName(compositeElement: HaskellCompositeElement): String = {
    val psiElement: PsiElement = findFirstVarIdTokenChildPsiElement(compositeElement)
    if (psiElement != null) psiElement.getText else null
  }

  private def getFirstHaskellVar(DeclarationElement: HaskellDeclarationElement): HaskellVar = {
    PsiTreeUtil.findChildOfType(DeclarationElement, classOf[HaskellVar])
  }

  private def getFirstHaskellVarName(DeclarationElement: HaskellDeclarationElement): String = {
    val haskellVar: HaskellVar = getFirstHaskellVar(DeclarationElement)
    if (haskellVar != null) haskellVar.getName else null
  }

  private def findFirstConIdTokenChildPsiElement(compositeElement: HaskellCompositeElement): PsiElement = {
    val keyNode: ASTNode = compositeElement.getNode.findChildByType(HaskellTypes.HS_CON_ID)
    if (keyNode != null) keyNode.getPsi else null
  }

  private def findFirstConIdTokenChildPsiElementName(compositeElement: HaskellCompositeElement): String = {
    val psiElement: PsiElement = findFirstConIdTokenChildPsiElement(compositeElement)
    if (psiElement != null) psiElement.getText else null
  }
}