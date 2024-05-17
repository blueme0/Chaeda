package com.chaeda.domain.enumSet

enum class Subject(val koreanName: String, val chapters: List<Chapter>) {
    Math_high("수학 상", listOf(
        Chapter.Polynomial,
        Chapter.Equations,
        Chapter.Inequalities,
        Chapter.Equations_of_Shapes
    )),
    Math_low("수학 하", listOf(
        Chapter.Sets_and_Propositions,
        Chapter.Functions,
        Chapter.Permutations_and_Combinations
    )),
    Math_1("수학1", listOf(
        Chapter.Exponential_and_Logarithmic_Functions,
        Chapter.Trigonometric_Functions,
        Chapter.Sequences
    )),
    Math_2("수학2", listOf(
        Chapter.Functions_Limits_and_Continuity,
        Chapter.Differentiation,
        Chapter.Integration_in_Math_2
    )),
    Calculus("미적분", listOf(
        Chapter.Limits_of_Sequences,
        Chapter.Differential_Calculus,
        Chapter.Integration_in_calculus
    )),
    Probability_and_Statistics("확률과 통계", listOf(
        Chapter.Counting_Methods,
        Chapter.Probability,
        Chapter.Statistics
    )),
    Geometry("기하", listOf(
        Chapter.Conic_Sections,
        Chapter.Plane_Vectors,
        Chapter.Spatial_Shapes_and_Coordinates
    )),
    Mix("혼합형", listOf())
}