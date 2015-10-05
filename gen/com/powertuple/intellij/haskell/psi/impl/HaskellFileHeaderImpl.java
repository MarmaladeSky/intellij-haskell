// This is a generated file. Not intended for manual editing.
package com.powertuple.intellij.haskell.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.powertuple.intellij.haskell.psi.HaskellTypes.*;
import com.powertuple.intellij.haskell.psi.*;

public class HaskellFileHeaderImpl extends HaskellCompositeElementImpl implements HaskellFileHeader {

  public HaskellFileHeaderImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof HaskellVisitor) ((HaskellVisitor)visitor).visitFileHeader(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<HaskellFileHeaderPragma> getFileHeaderPragmaList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, HaskellFileHeaderPragma.class);
  }

}