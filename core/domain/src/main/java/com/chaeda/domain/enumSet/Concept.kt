package com.chaeda.domain.enumSet

enum class Concept(val chapter: String, val koreanName: String) {
    /*------------------수학 상-----------------------*/
    // Polynomial : 다항식
    Operations_of_polynomials(Chapter.Polynomial.name, "다항식의 연산"),
    Remainder_theorem_and_factorization(Chapter.Polynomial.name,"나머지정리와 인수분해"),

    // Equations : 방정식
    Complex_numbers(Chapter.Equations.name, "복소수"),
    Quadratic_equations(Chapter.Equations.name,  "이차방정식"),
    Quadratic_equations_and_quadratic_functions(Chapter.Equations.name,  "이차방정식과 이차함수"),
    Various_types_of_equations(Chapter.Equations.name, "여러 가지 방정식"),

    // Inequalities : 부등식
    Linear_inequalities(Chapter.Inequalities.name, "일차부등식"),
    Quadratic_inequalities(Chapter.Inequalities.name, "이차부등식"),

    // Equations_of_Shapes : 도형의 방정식
    Plane_coordinates(Chapter.Equations_of_Shapes.name, "평면좌표"),
    Equations_of_lines(Chapter.Equations_of_Shapes.name, "직선의 방정식"),
    Equations_of_circles(Chapter.Equations_of_Shapes.name, "원의 방정식"),
    Transformation_of_shapes(Chapter.Equations_of_Shapes.name, "도형의 이동"),


    /*------------------수학 하-----------------------*/
    // Sets_and_Propositions : 집합과 명제
    Meaning_and_representation_of_sets(Chapter.Sets_and_Propositions.name, "집합의 뜻과 표현"),
    Operations_of_sets(Chapter.Sets_and_Propositions.name, "집합의 연산"),
    Propositions(Chapter.Sets_and_Propositions.name, "명제"),

    // Functions : 함수
    Functions(Chapter.Functions.name, "함수"),
    Rational_expressions_and_rational_functions(Chapter.Functions.name, "유리식과 유리함수"),
    Irrational_expressions_and_irrational_functions(Chapter.Functions.name, "무리식과 무리함수"),

    // Permutations_and_Combinations : 순열과 조합
    Permutations_and_combinations(Chapter.Permutations_and_Combinations.name, "순열과 조합"),


    /*------------------수1-----------------------*/
    //Exponential_and_Logarithmic_Functions : 지수함수와 로그함수
    Exponents_and_Logarithms(Chapter.Exponential_and_Logarithmic_Functions.name, "지수와 로그"),
    Exponential_and_Logarithmic_Functions(Chapter.Exponential_and_Logarithmic_Functions.name, "지수함수와 로그함수"),

    //Trigonometric_Functions : 삼각함수
    Trigonometric_Functions(Chapter.Trigonometric_Functions.name, "삼각함수"),
    Graphs_of_Trigonometric_Functions(Chapter.Trigonometric_Functions.name, "삼각함수의 그래프"),
    Applications_of_Trigonometric_Functions(Chapter.Trigonometric_Functions.name, "삼각함수의 활용"),

    //Sequences : 수열
    Arithmetic_and_Geometric_Sequences(Chapter.Sequences.name, "등차수열과 등비수열"),
    Sum_of_Sequences(Chapter.Sequences.name, "수열의 합"),
    Mathematical_Induction(Chapter.Sequences.name, "수학적 귀납법"),


    /*------------------수학 II-----------------------*/
    // Functions_Limits_and_Continuity : 함수의 극한과 연속
    Functions_Limits(Chapter.Functions_Limits_and_Continuity.name, "함수의 극한"),
    Functions_Continuity(Chapter.Functions_Limits_and_Continuity.name, "함수의 연속"),

    // Differentiation : 미분
    Differential_Coefficient(Chapter.Differentiation.name, "미분계수"),
    Derivative_Functions(Chapter.Differentiation.name, "도함수"),
    Applications_of_Derivatives(Chapter.Differentiation.name, "도함수의 활용"),

    // Integration : 적분
    Indefinite_Integrals(Chapter.Integration_in_Math_2.name, "부정적분"),
    Definite_Integrals(Chapter.Integration_in_Math_2.name, "정적분"),
    Applications_of_Definite_Integrals_in_Math_2(Chapter.Integration_in_Math_2.name, "정적분의 활용"),


    /*------------------미적분-----------------------*/
    // Limits_of_Sequences : 수열의 극한
    Limits_of_Sequences(Chapter.Limits_of_Sequences.name, "수열의 극한"),
    Series(Chapter.Limits_of_Sequences.name, "급수"),

    // Differential_Calculus : 미분법
    Differentiation_of_Various_Functions(Chapter.Differential_Calculus.name, "여러 가지 함수의 미분"),
    Various_Differentiation_Methods(Chapter.Differential_Calculus.name, "여러 가지 미분법"),
    Applications_of_Derivative_Functions(Chapter.Differential_Calculus.name, "도함수의 활용"),

    // Integration : 적분법
    Various_Integration_Methods(Chapter.Integration_in_calculus.name, "여러 가지 적분법"),
    Applications_of_Definite_Integrals_in_Calculus(Chapter.Integration_in_calculus.name, "정적분의 활용"),


    /*------------------확률과 통계-----------------------*/
    // Counting_Methods : 경우의 수
    Permutations_and_Combinations(Chapter.Counting_Methods.name, "순열과 조합"),
    Binomial_Theorem(Chapter.Counting_Methods.name, "이항정리"),

    // Probability : 확률
    Probability(Chapter.Probability.name, "확률"),
    Conditional_Probability(Chapter.Probability.name, "조건부확률"),

    // Statistics : 통계
    Probability_Distributions(Chapter.Statistics.name, "확률분포"),
    Statistical_Estimation(Chapter.Statistics.name, "통계적 추정"),


    /*------------------기하-----------------------*/
    // Conic_Sections : 이차곡선
    Conic_Sections(Chapter.Conic_Sections.name, "이차곡선"),
    Conic_Sections_and_Lines(Chapter.Conic_Sections.name, "이차곡선과 직선"),

    // Plane_Vectors : 평면벡터
    Vector_Operations(Chapter.Plane_Vectors.name, "벡터의 연산"),
    Components_and_Dot_Product_of_Plane_Vectors(Chapter.Plane_Vectors.name, "평면벡터의 성분과 내적"),

    // Spatial_Shapes_and_Coordinates : 공간도형과 공간좌표
    Spatial_Shapes(Chapter.Spatial_Shapes_and_Coordinates.name, "공간도형"),
    Spatial_Coordinates(Chapter.Spatial_Shapes_and_Coordinates.name, "공간좌표")
}