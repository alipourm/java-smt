/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2015  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.sosy_lab.solver.basicimpl;

import com.google.common.collect.Lists;

import org.sosy_lab.solver.SolverException;
import org.sosy_lab.solver.api.BooleanFormula;
import org.sosy_lab.solver.api.Formula;
import org.sosy_lab.solver.api.QuantifiedFormulaManager;

import java.util.List;

public abstract class AbstractQuantifiedFormulaManager<TFormulaInfo, TType, TEnv, TFuncDecl>
    extends AbstractBaseFormulaManager<TFormulaInfo, TType, TEnv, TFuncDecl>
    implements QuantifiedFormulaManager {

  protected AbstractQuantifiedFormulaManager(FormulaCreator<TFormulaInfo, TType, TEnv, TFuncDecl>
                                                 pCreator) {
    super(pCreator);
  }

  private BooleanFormula wrap(TFormulaInfo formulaInfo) {
    return getFormulaCreator().encapsulateBoolean(formulaInfo);
  }

  @Override
  public BooleanFormula exists(List<? extends Formula> pVariables, BooleanFormula pBody) {
    return wrap(exists(Lists.transform(pVariables, extractor), extractInfo(pBody)));
  }

  protected abstract TFormulaInfo exists(List<TFormulaInfo> pVariables, TFormulaInfo pBody);

  @Override
  public final BooleanFormula forall(List<? extends Formula> pVariables, BooleanFormula pBody) {
    return wrap(forall(Lists.transform(pVariables, extractor), extractInfo(pBody)));
  }

  protected abstract TFormulaInfo forall(List<TFormulaInfo> pVariables, TFormulaInfo pBody);

  @Override
  public BooleanFormula eliminateQuantifiers(BooleanFormula pF)
      throws InterruptedException, SolverException {
    return wrap(eliminateQuantifiers(extractInfo(pF)));
  }

  protected abstract TFormulaInfo eliminateQuantifiers(TFormulaInfo pExtractInfo)
      throws SolverException, InterruptedException;

  @Override
  public BooleanFormula mkQuantifier(
      Quantifier q, List<? extends Formula> pVariables, BooleanFormula pBody) {
    return wrap(mkQuantifier(q, Lists.transform(pVariables, extractor), extractInfo(pBody)));
  }

  public abstract TFormulaInfo mkQuantifier(
      Quantifier q, List<TFormulaInfo> vars, TFormulaInfo body);
}
