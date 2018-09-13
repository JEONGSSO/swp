QUnit.test("테스트", (assert) => {
	assert.equal(1, 1, "패스");
	assert.ok(1 == '1', " assert.OK pass");		// assert.ok(state 맞으면 실행)
//	assert.strictEqual(1 == '1', " assert.strictEqual pass");
	
	let arr1 = [1, 2, 3],
		arr2 = [1, 2, 3],
		arr3 = [1, 3, 2];
	
	assert.equal(arr1, arr2, "equal 1 = 2");
	assert.strictEqual(arr1, arr2, "assert.strictEqual 1 = 2");
	assert.deepEqual(arr1, arr2, "assert.deepEqual 1 d = 2");	//OK
	
	assert.deepEqual(arr3, arr2, "assert.deepEqual 3 d = 2");

	let j1 = {id : 1, name : hong},
		j2 = {id : 2, name : hong},
		j3 = {id : 3, name : hong12};

	assert.equal(j1, j2, "equal j1 = j2");
	assert.deepEqual(j1, j2, "assert.strictEqual j1 = j2");
	assert.deepEqual(j1, j3, "assert.deepEqual j1 = j3");	//OK
	
});