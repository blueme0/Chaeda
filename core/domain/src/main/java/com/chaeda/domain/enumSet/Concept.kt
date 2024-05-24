package com.chaeda.domain.enumSet

enum class Concept(val chapter: String, val koreanName: String) {
    /*------------------수학 상-----------------------*/
    // Polynomial : 다항식
    Operations_of_polynomials("Polynomial", "다항식의 연산"),
    Remainder_theorem_and_factorization("Polynomial","나머지정리와 인수분해"),

    // Equations : 방정식
    Complex_numbers("Equations", "복소수"),
    Quadratic_equations("Equations",  "이차방정식"),
    Quadratic_equations_and_quadratic_functions("Equations",  "이차방정식과 이차함수"),
    Various_types_of_equations("Equations", "여러 가지 방정식"),

    // Inequalities : 부등식
    Linear_inequalities("Inequalities", "일차부등식"),
    Quadratic_inequalities("Inequalities", "이차부등식"),

    // Equations_of_Shapes : 도형의 방정식
    Plane_coordinates("Equations_of_Shapes", "평면좌표"),
    Equations_of_lines("Equations_of_Shapes", "직선의 방정식"),
    Equations_of_circles("Equations_of_Shapes", "원의 방정식"),
    Transformation_of_shapes("Equations_of_Shapes", "도형의 이동"),


    /*------------------수학 하-----------------------*/
    // Sets_and_Propositions : 집합과 명제
    Meaning_and_representation_of_sets("Sets_and_Propositions", "집합의 뜻과 표현"),
    Operations_of_sets("Sets_and_Propositions", "집합의 연산"),
    Propositions("Sets_and_Propositions", "명제"),

    // Functions : 함수
    Functions("Functions", "함수"),
    Rational_expressions_and_rational_functions("Functions", "유리식과 유리함수"),
    Irrational_expressions_and_irrational_functions("Functions", "무리식과 무리함수"),

    // Permutations_and_Combinations : 순열과 조합
    Permutations_and_combinations("Permutations_and_Combinations", "순열과 조합"),


    /*------------------수1-----------------------*/
    //Exponential_and_Logarithmic_Functions : 지수함수와 로그함수
    Exponents_and_Logarithms("Exponential_and_Logarithmic_Functions", "지수와 로그"),
    Exponential_and_Logarithmic_Functions("Exponential_and_Logarithmic_Functions", "지수함수와 로그함수"),

    //Trigonometric_Functions : 삼각함수
    Trigonometric_Functions("Trigonometric_Functions", "삼각함수"),
    Graphs_of_Trigonometric_Functions("Trigonometric_Functions", "삼각함수의 그래프"),
    Applications_of_Trigonometric_Functions("Trigonometric_Functions", "삼각함수의 활용"),

    //Sequences : 수열
    Arithmetic_and_Geometric_Sequences("Sequences", "등차수열과 등비수열"),
    Sum_of_Sequences("Sequences", "수열의 합"),
    Mathematical_Induction("Sequences", "수학적 귀납법"),


    /*------------------수학 II-----------------------*/
    // Functions_Limits_and_Continuity : 함수의 극한과 연속
    Functions_Limits("Functions_Limits_and_Continuity", "함수의 극한"),
    Functions_Continuity("Functions_Limits_and_Continuity", "함수의 연속"),

    // Differentiation : 미분
    Differential_Coefficient("Differentiation", "미분계수"),
    Derivative_Functions("Differentiation", "도함수"),
    Applications_of_Derivatives("Differentiation", "도함수의 활용"),

    // Integration : 적분
    Indefinite_Integrals("Integration_in_Math_2", "부정적분"),
    Definite_Integrals("Integration_in_Math_2", "정적분"),
    Applications_of_Definite_Integrals_in_Math_2("Integration_in_Math_2", "정적분의 활용"),


    /*------------------미적분-----------------------*/
    // Limits_of_Sequences : 수열의 극한
    Limits_of_Sequences("Limits_of_Sequences", "수열의 극한"),
    Series("Limits_of_Sequences", "급수"),

    // Differential_Calculus : 미분법
    Differentiation_of_Various_Functions("Differential_Calculus", "여러 가지 함수의 미분"),
    Various_Differentiation_Methods("Differential_Calculus", "여러 가지 미분법"),
    Applications_of_Derivative_Functions("Differential_Calculus", "도함수의 활용"),

    // Integration : 적분법
    Various_Integration_Methods("Integration_in_calculus", "여러 가지 적분법"),
    Applications_of_Definite_Integrals_in_Calculus("Integration_in_calculus", "정적분의 활용"),


    /*------------------확률과 통계-----------------------*/
    // Counting_Methods : 경우의 수
    Permutations_and_Combinations("Counting_Methods", "순열과 조합"),
    Binomial_Theorem("Counting_Methods", "이항정리"),

    // Probability : 확률
    Probability("Probability", "확률"),
    Conditional_Probability("Probability", "조건부확률"),

    // Statistics : 통계
    Probability_Distributions("Statistics", "확률분포"),
    Statistical_Estimation("Statistics", "통계적 추정"),


    /*------------------기하-----------------------*/
    // Conic_Sections : 이차곡선
    Conic_Sections("Conic_Sections", "이차곡선"),
    Conic_Sections_and_Lines("Conic_Sections", "이차곡선과 직선"),

    // Plane_Vectors : 평면벡터
    Vector_Operations("Plane_Vectors", "벡터의 연산"),
    Components_and_Dot_Product_of_Plane_Vectors("Plane_Vectors", "평면벡터의 성분과 내적"),

    // Spatial_Shapes_and_Coordinates : 공간도형과 공간좌표
    Spatial_Shapes("Spatial_Shapes_and_Coordinates", "공간도형"),
    Spatial_Coordinates("Spatial_Shapes_and_Coordinates", "공간좌표")
}