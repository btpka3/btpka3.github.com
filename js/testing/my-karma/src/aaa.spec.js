describe("A suite of basic functions", function () {
    it("reverse word", function () {
        expect("DCBA").toEqual(reverse("ABCD"));
        //debugger;
        expect("Conan").toEqual(reverse("nano"));
    });
});