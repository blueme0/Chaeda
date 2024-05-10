package com.chaeda.domain.enumSet

enum class Chapter(val englishName: String, val koreanName: String, val concepts: List<Concept>) {
    // 수학 상
    Polynomial("Polynomial", "다항식", listOf(
        Concept.Operations_of_polynomials,
        Concept.Remainder_theorem_and_factorization
    )),
    Equations("Equations", "방정식", listOf(
        Concept.Complex_numbers,
        Concept.Quadratic_equations,
        Concept.Quadratic_equations_and_quadratic_functions,
        Concept.Various_types_of_equations
    )),
    Inequalities("Inequalities", "부등식", listOf(
        Concept.Linear_inequalities,
        Concept.Quadratic_inequalities
    )),
    Equations_of_Shapes("Equations_of_Shapes", "도형의 방정식", listOf(
        Concept.Plane_coordinates,
        Concept.Equations_of_lines,
        Concept.Equations_of_circles,
        Concept.Transformation_of_shapes
    )),

    //수학 하
    Sets_and_Propositions("Sets_and_Propositions", "집합과 명제", listOf(
        Concept.Meaning_and_representation_of_sets,
        Concept.Operations_of_sets,
        Concept.Propositions
    )),
    Functions("Functions", "함수", listOf(
        Concept.Functions,
        Concept.Rational_expressions_and_rational_functions,
        Concept.Irrational_expressions_and_irrational_functions
    )),
    Permutations_and_Combinations("Permutations_and_Combinations", "순열과 조합", listOf(
        Concept.Permutations_and_combinations
    )),

    //수학1
    Exponential_and_Logarithmic_Functions("Exponential_and_Logarithmic_Functions", "지수함수와 로그함수", listOf(
        Concept.Exponents_and_Logarithms,
        Concept.Exponential_and_Logarithmic_Functions
    )),
    Trigonometric_Functions("Trigonometric_Functions", "삼각함수", listOf(
        Concept.Trigonometric_Functions,
        Concept.Graphs_of_Trigonometric_Functions,
        Concept.Applications_of_Trigonometric_Functions
    )),
    Sequences("Sequences", "수열", listOf(
        Concept.Arithmetic_and_Geometric_Sequences,
        Concept.Sum_of_Sequences,
        Concept.Mathematical_Induction
    )),

    //수학2
    Functions_Limits_and_Continuity("Functions_Limits_and_Continuity", "함수의 극한과 연속", listOf(
        Concept.Functions_Limits,
        Concept.Functions_Continuity
    )),
    Differentiation("Differentiation", "미분", listOf(
        Concept.Differential_Coefficient,
        Concept.Derivative_Functions,
        Concept.Applications_of_Derivatives
    )),
    Integration_in_Math_2("Integration_in_Math_2", "적분", listOf(
        Concept.Indefinite_Integrals,
        Concept.Definite_Integrals,
        Concept.Applications_of_Definite_Integrals_in_Math_2
    )),

    //미적분
    Limits_of_Sequences("Limits_of_Sequences", "수열의 극한", listOf(
        Concept.Limits_of_Sequences,
        Concept.Series
    )),
    Differential_Calculus("Differential_Calculus", "미분법", listOf(
        Concept.Differentiation_of_Various_Functions,
        Concept.Various_Differentiation_Methods,
        Concept.Applications_of_Derivative_Functions
    )),
    Integration_in_calculus("Integration_in_calculus", "적분법", listOf(
        Concept.Various_Integration_Methods,
        Concept.Applications_of_Definite_Integrals_in_Calculus
    )),

    //확률과 통계
    Counting_Methods("Counting_Methods", "경우의 수", listOf(
        Concept.Permutations_and_Combinations,
        Concept.Binomial_Theorem
    )),
    Probability("Probability", "확률", listOf(
        Concept.Probability,
        Concept.Conditional_Probability
    )),
    Statistics("Statistics", "통계", listOf(
        Concept.Probability_Distributions,
        Concept.Statistical_Estimation
    )),

    //기하
    Conic_Sections("Conic_Sections", "이차곡선", listOf(
        Concept.Conic_Sections,
        Concept.Conic_Sections_and_Lines
    )),
    Plane_Vectors("Plane_Vectors", "평면벡터", listOf(
        Concept.Vector_Operations,
        Concept.Components_and_Dot_Product_of_Plane_Vectors
    )),
    Spatial_Shapes_and_Coordinates("Spatial_Shapes_and_Coordinates", "공간도형과 공간좌표", listOf(
        Concept.Spatial_Shapes,
        Concept.Spatial_Coordinates
    ))
}